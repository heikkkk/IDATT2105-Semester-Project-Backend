package no.ntnu.idi.idatt2105.quizopia.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class History {

    private Long historyId;
    private LocalDateTime completedAt;
    private Integer score;
    private Integer rating;
    private String feedbackText;
    private Long userId; // Foreign key reference to User
    private Long quizId; // Foreign key reference to Quiz
}
