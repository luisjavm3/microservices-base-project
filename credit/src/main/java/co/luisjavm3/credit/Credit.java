package co.luisjavm3.credit;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Credit {
	private Long id;
	private Long userId;
	private Double amount;
	@Builder.Default
	private LocalDateTime createdAt = LocalDateTime.now();
	@Builder.Default
	private LocalDateTime updatedAt = null;
	@Builder.Default
	private LocalDateTime deletedAt = null;

	public boolean isActive() {
		return deletedAt == null;
	}
}
