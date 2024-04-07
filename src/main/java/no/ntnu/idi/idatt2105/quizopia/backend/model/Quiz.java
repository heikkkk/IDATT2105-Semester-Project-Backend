package no.ntnu.idi.idatt2105.quizopia.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {

    private Long quizId;
    private String title;
    private String description;
    private boolean isPublic;
    private LocalDateTime createdAt;
    private Long templateId; // Foreign key reference to Template
    private Long categoryId; // Foreign key reference to Category
    private Long mediaId;    // Foreign key reference to MultiMedia
}
