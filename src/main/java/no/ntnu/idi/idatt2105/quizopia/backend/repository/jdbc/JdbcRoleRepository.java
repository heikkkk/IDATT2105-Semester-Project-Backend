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
  @Override
  public Optional<String> findTypeById(Long id) {
    String sql = "SELECT type FROM roles WHERE role_id=?";
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
