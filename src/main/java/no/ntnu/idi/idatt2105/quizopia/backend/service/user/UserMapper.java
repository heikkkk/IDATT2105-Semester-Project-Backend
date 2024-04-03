package no.ntnu.idi.idatt2105.quizopia.backend.service.user;

import lombok.RequiredArgsConstructor;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.UserRegistrationDto;
import no.ntnu.idi.idatt2105.quizopia.backend.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Implementation of logout functionality for revoking refresh tokens and clearing the refresh token cookie.
 */
@Component
@RequiredArgsConstructor
public class UserMapper {

  private final PasswordEncoder passwordEncoder;

  /**
   * Maps a {@link UserRegistrationDto} object to a {@link User} object
   * @param userRegistrationDto to be mapped
   * @return the mapped {@link User} object
   */
  public User toUser(UserRegistrationDto userRegistrationDto) {
    User user = new User();
    user.setUsername(userRegistrationDto.username());
    user.setPassword(passwordEncoder.encode(userRegistrationDto.password()));
    user.setEmail(userRegistrationDto.email());
    user.setRoleId(userRegistrationDto.roleId());
    return user;
  }
}
