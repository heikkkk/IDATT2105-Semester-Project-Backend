package no.ntnu.idi.idatt2105.quizopia.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultiMedia {

    private Long mediaId;
    private String filePath;
    private String description;
    private LocalDateTime createdAt;
    private Long typeId; // Foreign key reference to MediaType
}
