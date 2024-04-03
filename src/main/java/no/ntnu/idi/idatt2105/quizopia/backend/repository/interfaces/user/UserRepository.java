package no.ntnu.idi.idatt2105.quizopia.backend.repository.interfaces.user;

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

  Optional<String> findRoleById(Long userId);

  Optional<String> findRoleByName(String username);

  int updateUsername(Long userId, String username);

  int updatePassword(Long userId, String password);

  int updateEmail(Long userId, String email);

  int saveAll(List<User> users);

  Optional<Long> findIdByName(String username);

}
