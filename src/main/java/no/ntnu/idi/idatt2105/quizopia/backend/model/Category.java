package no.ntnu.idi.idatt2105.quizopia.backend.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Category {

    private Long categoryId;
    private String name;
    private String description;
}
