package no.ntnu.idi.idatt2105.quizopia.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompletionFeedback {

    private Long feedbackId;
    private String text;
}
