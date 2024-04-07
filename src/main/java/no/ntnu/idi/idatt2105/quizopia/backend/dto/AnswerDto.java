package no.ntnu.idi.idatt2105.quizopia.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDto {

    private Long answerId;
    private String answerText;
    private Boolean isCorrect;
}
