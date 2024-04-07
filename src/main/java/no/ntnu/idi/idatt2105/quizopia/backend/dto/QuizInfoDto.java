package no.ntnu.idi.idatt2105.quizopia.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizInfoDto {
    private Long quizId;
    private String quizTitle;
    private Long categoryId;
    private String author;
    private String thumbnailFilepath;
}
