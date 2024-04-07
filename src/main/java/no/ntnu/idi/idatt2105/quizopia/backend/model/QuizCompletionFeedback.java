package no.ntnu.idi.idatt2105.quizopia.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizCompletionFeedback {

    private Long quizId; // Foreign key reference to Quiz
    private Long feedbackId; // Foreign key reference to CompletionFeedback
    private Integer scoreLowerBound;
    private Integer scoreUpperBound;
}
