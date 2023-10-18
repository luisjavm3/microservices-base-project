package co.luisjavm3.payment;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Payment {
	private Long id;
	private Float amount;
	private Long creditId;
	@Builder.Default
	private LocalDateTime createdAt = LocalDateTime.now();
	@Builder.Default
	private LocalDateTime updatedAt = null;
}
