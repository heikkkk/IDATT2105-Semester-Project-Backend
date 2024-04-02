package no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.RoleRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcRoleRepository implements RoleRepository {

  private final JdbcTemplate jdbcTemplate;

  /**
   * Find the type of a {@link no.ntnu.idi.idatt2105.quizopia.backend.model.Role Role} from the
   * database given the ID
   * @param id The ID of the {@link no.ntnu.idi.idatt2105.quizopia.backend.model.Role Role}
   * @return the type of the {@link no.ntnu.idi.idatt2105.quizopia.backend.model.Role Role} if
   * it exists
   */
  @Override
  public Optional<String> findTypeById(Long id) {
    String sql = "SELECT type FROM role WHERE role_id=?";
    try {
      String role = jdbcTemplate.queryForObject(
          sql,
          new Object[]{id},
          (rs, rowNum) -> rs.getString("type")
      );
      return Optional.of(role);
    } catch (Exception e) {
      return Optional.empty();
      //TODO on return empty throw new exception
    }

  }
}
