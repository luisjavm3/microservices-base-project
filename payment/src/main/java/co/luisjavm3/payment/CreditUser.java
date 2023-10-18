package co.luisjavm3.payment;

import java.time.LocalDateTime;

public record CreditUser(Long id, User user, Double amount, LocalDateTime createdAt, LocalDateTime updatedAt,
		LocalDateTime deletedAt, boolean active) {

}
