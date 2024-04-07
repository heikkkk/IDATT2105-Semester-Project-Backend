package no.ntnu.idi.idatt2105.quizopia.backend.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizDto {
    private Long quizId;
    private String title;
    private String description;
    @JsonProperty("isPublic")
    private boolean isPublic;
    private LocalDateTime createdAt;
    private Long templateId; // Foreign key reference to Template
    private Long categoryId; // Foreign key reference to Category 
    private Long mediaId;    // Foreign key reference to Multi_Medias;
    private Long userId;
    private List<QuestionDto> questions;
}
