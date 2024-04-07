package no.ntnu.idi.idatt2105.quizopia.backend.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Collaborator {

    private Long userId; // Foreign key reference to User
    private Long quizId; // Foreign key reference to Quiz
    private Long typeId; // Foreign key reference to TypeCollaborator
}
