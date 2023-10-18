package co.luisjavm3.user;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class User {
    private Long id;
    private String username;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
