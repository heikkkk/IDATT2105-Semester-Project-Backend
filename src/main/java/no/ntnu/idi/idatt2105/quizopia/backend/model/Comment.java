package no.ntnu.idi.idatt2105.quizopia.backend.model;

import java.time.LocalDateTime;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    private Long commentId;
    private String text;
    private LocalDateTime createdAt;
    private Long userId; // Foreign key reference to User
    private Long quizId; // Foreign key reference to Quiz
}
