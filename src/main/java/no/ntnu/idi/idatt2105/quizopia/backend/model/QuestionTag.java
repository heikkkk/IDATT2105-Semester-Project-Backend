package no.ntnu.idi.idatt2105.quizopia.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionTag {

    private Long tagId; // Foreign key reference to Tag
    private Long questionId; // Foreign key reference to Question
}
