package no.ntnu.idi.idatt2105.quizopia.backend.service;

import lombok.RequiredArgsConstructor;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.UserRegistrationDto;
import no.ntnu.idi.idatt2105.quizopia.backend.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

  private final PasswordEncoder passwordEncoder;

  public User toUser(UserRegistrationDto userRegistrationDto) {
    User user = new User();
    user.setUsername(userRegistrationDto.username());
    user.setPassword(passwordEncoder.encode(userRegistrationDto.password()));
    user.setEmail(userRegistrationDto.email());
    user.setRoleId(userRegistrationDto.roleId());
    return user;
  }
}
