package no.ntnu.idi.idatt2105.quizopia.backend.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Template {

    private Long templateId;
    private String name;
    private String description;
    private String filepath;
    private LocalDateTime createdAt;
    private Long userId; 
}
