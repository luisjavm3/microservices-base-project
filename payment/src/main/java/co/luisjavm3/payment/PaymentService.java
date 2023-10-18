package co.luisjavm3.payment;

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
public class PaymentService {
	private final CreditClient client;
	private static Logger logger = LoggerFactory.getLogger(PaymentService.class);

	private static List<Payment> payments = new ArrayList<Payment>(
			Arrays.asList(Payment.builder().id(1l).creditId(1l).amount(5.0f).build(),
					Payment.builder().id(2l).creditId(1l).amount(5.0f).build(),
					Payment.builder().id(3l).creditId(1l).amount(5.0f).build(),
					Payment.builder().id(4l).creditId(1l).amount(5.0f).build(),
					Payment.builder().id(5l).creditId(1l).amount(5.0f).build(),
					Payment.builder().id(6l).creditId(6l).amount(10.0f).build(),
					Payment.builder().id(7l).creditId(6l).amount(10.0f).build(),
					Payment.builder().id(8l).creditId(6l).amount(10.0f).build(),
					Payment.builder().id(9l).creditId(6l).amount(10.0f).build(),
					Payment.builder().id(10l).creditId(6l).amount(10.0f).build()));

	private Long generateId() {
		Long maxId = payments.stream().max(Comparator.comparing(Payment::getId)).get().getId();
		return maxId + 1;
	}

	public Payment addPayment(Payment payment) {
		CreditUser credit = client.getCredit(payment.getCreditId());

		if (credit == null) {
			logger.warn("No credit with Id: " + payment.getCreditId() + " was found!");
			return null;
		}

		Double totalPaid = payments.stream().filter(p -> p.getCreditId() == credit.id()).mapToDouble(Payment::getAmount)
				.sum();
		Double balance = credit.amount() - totalPaid;

		if (balance == 0) {
			logger.info("Credit with Id: " + payment.getCreditId() + " is already paid!");
			return null;
		}

		if (payment.getAmount() == 0 || payment.getAmount() == null) {
			logger.warn("Payment amount is zero or null");
			return null;
		}

		if (balance - payment.getAmount() < 0) {
			logger.warn("The balance of the credit(" + balance + ") is lower that the payment amount("
					+ payment.getAmount() + ")");

			return null;
		}

		payment.setId(generateId());
		payment.setCreatedAt(LocalDateTime.now());
		payment.setUpdatedAt(null);
		payments.add(payment);

		if (balance - payment.getAmount() == 0) {
			logger.info("Credit with Id: " + payment.getCreditId() + " payed off");
			client.softDeleteCredit(payment.getCreditId());
		}

		return payment;
	}

	public List<Payment> getPaymentsByCreditId(Long creditId) {
		return payments.stream().filter(p -> p.getCreditId() == creditId).toList();
	}

	public void deletePaymentsByCreditId(Long creditId) {
		payments = payments.stream().filter(p -> p.getCreditId() != creditId).toList();
	}
}
