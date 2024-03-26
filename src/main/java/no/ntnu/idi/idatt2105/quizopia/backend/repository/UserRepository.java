package no.ntnu.idi.idatt2105.quizopia.backend.repository;

import java.util.List;
import java.util.Optional;
import no.ntnu.idi.idatt2105.quizopia.backend.model.User;

public interface UserRepository {

  int save(User user);

  int delete(Long userId);

  Optional<User> findById(Long userId);

  Optional<User> findByName(String username);

  Optional<User> findByEmail(String email);

  Optional<List<User>> findAll();

  int updateUsername(Long userId, String username);

  int updatePassword(Long userId, String password);

  int updateEmail(Long userId, String email);

}
