package no.ntnu.idi.idatt2105.quizopia.backend.dto;

public record UserRegistrationDto(
    String username,
    String password,
    String email,
    Long roleId

) {
}
