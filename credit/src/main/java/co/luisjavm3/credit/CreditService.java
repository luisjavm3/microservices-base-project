package co.luisjavm3.credit;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreditService {
	private final UserClient userClient;
	private final PaymentClient paymentClient;
	private static Logger logger = LoggerFactory.getLogger(CreditService.class);

	private static List<Credit> credits = new ArrayList<Credit>(
			Arrays.asList(Credit.builder().id(1l).userId(1l).amount(100.0).build(),
					Credit.builder().id(2l).userId(1l).amount(150.0).build(),
					Credit.builder().id(3l).userId(1l).amount(200.0).build(),
					Credit.builder().id(4l).userId(1l).amount(250.0).build(),
					Credit.builder().id(5l).userId(1l).amount(300.0).build(),
					Credit.builder().id(6l).userId(2l).amount(100.0).build(),
					Credit.builder().id(7l).userId(2l).amount(150.0).build(),
					Credit.builder().id(8l).userId(2l).amount(200.0).build(),
					Credit.builder().id(9l).userId(2l).amount(250.0).build(),
					Credit.builder().id(10l).userId(2l).amount(300.0).build()));

	private Long generateId() {
		Long maxId = credits.stream().max(Comparator.comparing(Credit::getId)).get().getId();

		return maxId + 1;
	}

	public List<Credit> getAllCredits() {
		return credits;
	}

	public CreditUser getCreditById(Long id) {
		try {
			Credit credit = credits.stream().filter(c -> c.getId() == id).findFirst().orElse(null);

			if (credit == null)
				return null;

			User user = userClient.getUserById(credit.getUserId());
			List<Payment> payments = paymentClient.getPaymentsByCreditId(id);

			if (payments == null)
				payments = new ArrayList<Payment>();

			Double totalPaid = payments.stream().mapToDouble(Payment::getAmount).sum();
			Double balance = credit.getAmount() - totalPaid;

			CreditUser result = new CreditUser(id, user, credit.getAmount(), balance, credit.getCreatedAt(),
					credit.getUpdatedAt(), credit.getDeletedAt(), credit.isActive(), payments);

			return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public Credit addCredit(Credit credit) {
		User user = userClient.getUserById(credit.getUserId());

		if (user == null)
			return null;

		credit.setId(generateId());
		credits.add(credit);
		return credit;
	}

	public void removeCredit(Long id) {
		Credit credit = credits.stream().filter(c -> c.getId() == id).findFirst().orElse(null);

		if (credit == null)
			return;

		credits.remove(credit);
		paymentClient.deletePaymentsByCreditId(id);
	}

	public void removeCreditsByUserId(Long userId) {
		List<Credit> toDeleteCredits = credits.stream().filter(c -> c.getUserId() == userId).toList();
		credits = credits.stream().filter(c -> c.getUserId() != userId).toList();

		toDeleteCredits.forEach(c -> {
			paymentClient.deletePaymentsByCreditId(c.getId());
		});
	}

	public Credit softDeleteCreditById(Long id) {
		Credit creditDb = credits.stream().filter(c -> c.getId() == id).findFirst().orElse(null);

		if (creditDb == null)
			return null;

		creditDb.setDeletedAt(LocalDateTime.now());
		return creditDb;
	}
}
