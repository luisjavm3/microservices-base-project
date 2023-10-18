package co.luisjavm3.credit;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Payment {
	private Long id;
	private Float amount;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
