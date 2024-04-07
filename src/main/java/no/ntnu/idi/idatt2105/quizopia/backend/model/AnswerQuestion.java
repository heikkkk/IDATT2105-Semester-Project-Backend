package no.ntnu.idi.idatt2105.quizopia.backend.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AnswerQuestion {

    private Long questionId; // Foreign key reference to Question
    private Long answerId;  // Foreign key reference to Answer
    private Boolean isCorrect;
}
