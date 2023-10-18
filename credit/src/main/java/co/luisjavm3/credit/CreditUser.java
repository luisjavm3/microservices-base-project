package co.luisjavm3.credit;

import java.time.LocalDateTime;
import java.util.List;

public record CreditUser(Long id, User user, Double amount, Double balance, LocalDateTime createdAt,
		LocalDateTime updatedAt, LocalDateTime deletedAt, boolean active, List<Payment> payments) {

}
