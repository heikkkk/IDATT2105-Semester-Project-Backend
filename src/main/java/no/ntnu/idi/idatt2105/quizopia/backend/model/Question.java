package no.ntnu.idi.idatt2105.quizopia.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    private Long questionId;
    private String questionName;
    private String questionText;
    private String explanations;
    private int questionDuration;
    private Boolean isPublic;
    private Long typeId; // Foreign key reference to QuestionType
    private Long difficultyId; // Foreign key reference to DifficultyLevel
    private Long mediaId; // Foreign key reference to MultiMedia
}
