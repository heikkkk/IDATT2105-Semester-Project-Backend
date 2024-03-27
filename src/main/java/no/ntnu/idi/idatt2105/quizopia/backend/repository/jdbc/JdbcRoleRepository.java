package no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc;

import lombok.RequiredArgsConstructor;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Roles;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.RoleRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcRoleRepository implements RoleRepository {

  private final JdbcTemplate jdbcTemplate;
  @Override
  public String findTypeById(Long id) {
    String sql = "SELECT type FROM roles WHERE id=?";
    Roles role =  jdbcTemplate.queryForObject(
        sql,
        new BeanPropertyRowMapper<>(Roles.class),
        id
    );
    return role.getType();
  }
}
