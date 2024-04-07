package no.ntnu.idi.idatt2105.quizopia.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

    private Long feedbackId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Long userId; // Foreign key reference to User
}
