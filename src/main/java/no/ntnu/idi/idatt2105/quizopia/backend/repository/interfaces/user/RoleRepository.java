package no.ntnu.idi.idatt2105.quizopia.backend.repository.interfaces.user;

import java.util.Optional;

public interface RoleRepository {
  Optional<String> findTypeById(Long id);

}
