package co.luisjavm3.payment;

import java.time.LocalDate;

import lombok.Data;

@Data
public class User {
	private Long id;
	private String username;
	private LocalDate createdAt;
	private LocalDate updatedAt;
}
