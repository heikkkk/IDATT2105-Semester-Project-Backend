package no.ntnu.idi.idatt2105.quizopia.backend.service.user;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.idatt2105.quizopia.backend.model.User;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.interfaces.user.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public String findUsernameById(Long id) {
    Optional<User> user = userRepository.findById(id);
    if (user.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    return user.get().getUsername();
  }

  public Long findIdByUsername(String username) {
    Optional<Long> userId = userRepository.findIdByName(username);
    if (userId.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    return userId.get();
  }

  public Boolean updatePassword(Long userId, String newPassword) {
    int rowsAffected = userRepository.updatePassword(userId, passwordEncoder.encode(newPassword));
    if (rowsAffected != 0) {
        log.info("Password set successfully for user with ID: {}", userId);
    } else {
        log.info("Password was NOT set successfully for user with ID: {}", userId);
    }
    return rowsAffected!=0;
  }
}
