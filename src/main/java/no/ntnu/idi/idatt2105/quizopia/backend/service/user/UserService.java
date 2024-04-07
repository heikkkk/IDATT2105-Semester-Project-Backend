package no.ntnu.idi.idatt2105.quizopia.backend.service.user;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.idatt2105.quizopia.backend.model.User;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.interfaces.user.UserRepository;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.interfaces.quiz.CollaboratorRepository;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

  private final UserRepository userRepository;
  private final CollaboratorRepository collaboratorRepository;
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

  @Transactional
  public Boolean updatePassword(Long userId, String newPassword) {
    int rowsAffected = userRepository.updatePassword(userId, passwordEncoder.encode(newPassword));
    if (rowsAffected != 0) {
        log.info("Password set successfully for user with ID: {}", userId);
    } else {
        log.info("Password was NOT set successfully for user with ID: {}", userId);
    }
    return rowsAffected!=0;
  }

  @Transactional
  public Boolean updateUsername(Long userId, String newUsername) {
    int rowsAffected = userRepository.updateUsername(userId, newUsername);
    if (rowsAffected != 0) {
        log.info("Username set successfully for user with ID: {}", userId);
    } else {
        log.info("Username was NOT set successfully for user with ID: {}", userId);
    }
    return rowsAffected!=0;
  }

  @Transactional
  public Boolean deleteUser(Long userId) {

    int rowsAffectedCollaborator = collaboratorRepository.deleteUserById(userId);
    if (rowsAffectedCollaborator != 0) {
        log.info("Collaborator entries was successfully deleted for user with ID: {}", userId);
    } else {
        log.info("There was no Collaborator entries to delete for user with ID: {}", userId);
    }
    
    int rowsAffectedUser = userRepository.delete(userId);
    if (rowsAffectedUser != 0) {
        log.info("User was was successfully deleted with ID: {}", userId);
    } else {
        log.info("User was NOT successfully deleted with ID: {}", userId);
    }
    return rowsAffectedUser!=0;
  }

}
