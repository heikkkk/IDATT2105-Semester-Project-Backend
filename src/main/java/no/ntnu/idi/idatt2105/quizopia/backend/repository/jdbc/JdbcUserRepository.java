package no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc;

import java.util.List;
import java.util.Optional;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Roles;
import no.ntnu.idi.idatt2105.quizopia.backend.model.User;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.UserRepository;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUserRepository implements UserRepository {

  private final JdbcTemplate jdbcTemplate;

  public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public int save(User user) {
    String sql = "INSERT INTO users (username, password, email, role_id) VALUES(?,?,?,?)";
    return jdbcTemplate.update(
        sql,
        user.getUsername(), user.getPassword(), user.getEmail(), user.getRoleId());
  }

  @Override
  public int delete(Long userId) {
    return jdbcTemplate.update("DELETE FROM users WHERE id=?", userId);
  }

  @Override
  public Optional<User> findById(Long userId) {
    String sql = "SELECT * FROM users WHERE id=?";
    try {
      User user = jdbcTemplate.queryForObject(
          sql,
          BeanPropertyRowMapper.newInstance(User.class),
          userId);
      return Optional.ofNullable(user);
    } catch (IncorrectResultSizeDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<User> findByName(String username) {
    String sql = "SELECT * FROM users WHERE username=?";
    try {
      User user = jdbcTemplate.queryForObject(
          sql,
          BeanPropertyRowMapper.newInstance(User.class),
          username);
      return Optional.ofNullable(user);
    } catch (IncorrectResultSizeDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<User> findByEmail(String email) {
    String sql = "SELECT * FROM users WHERE email=?";
    try {
      User user = jdbcTemplate.queryForObject(
          sql,
          BeanPropertyRowMapper.newInstance(User.class),
          email);
      return Optional.ofNullable(user);
    } catch (IncorrectResultSizeDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<List<User>> findAll() {
    String sql = "SELECT * FROM users";
    try {
      List<User> users = jdbcTemplate.query(
          sql,
          new BeanPropertyRowMapper<>(User.class));
      return Optional.of(users);
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<String> findRoleById(Long userId) {
    String sql = "SELECT * FROM roles r JOIN users u ON r.id = u.role_id WHERE u.id=?";
    try {
      Roles role = jdbcTemplate.queryForObject(
          sql,
          BeanPropertyRowMapper.newInstance(Roles.class),
          userId
          );
      return Optional.of(role.getType());
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<String> findRoleByName(String username) {
    String sql = "SELECT * FROM roles r JOIN users u ON r.id = u.role_id WHERE u.username=?";
    try {
      Roles role = jdbcTemplate.queryForObject(
          sql,
          BeanPropertyRowMapper.newInstance(Roles.class),
          username
      );
      return Optional.of(role.getType());
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  @Override
  public int updateUsername(Long userId, String username) {
    return jdbcTemplate.update("UPDATE users SET username=? WHERE id=?", username, userId);
  }

  @Override
  public int updatePassword(Long userId, String password) {
    return jdbcTemplate.update("UPDATE users SET password=? WHERE id=?", password, userId);
  }

  @Override
  public int updateEmail(Long userId, String email) {
    return jdbcTemplate.update("UPDATE users SET email=? WHERE id=?", email, userId);
  }

  @Override
  public int saveAll(List<User> users) {
    int numAffected = 0;
    for (User user : users) {
      numAffected += save(user);
    }
    return numAffected;
  }

  /**
   * Finds the id of a user in the databse given their name.
   * This method uses deprecated [Object]RowMapper with the queryForObject method from
   * JdbcTemplate. <br>
   * Using a BeanPropertyRowMapper only gave empty Optinals.<br>
   * The [Object]RowMapper returns the actual id. <br>
   * <br>
   * This method should be refactored to not use deprecated methods.
   * @param username the username with the id to be found
   * @return the id if it exists. If not return an empty Optional
   */
  @Override
  public Optional<Long> findIdByName(String username) {
    String sql = "SELECT id FROM users WHERE username=?";
    try {
      Long id = jdbcTemplate.queryForObject(
          sql,
          new Object[]{username},
          (rs, rowNum) -> rs.getLong("id")
      );
      return Optional.of(id);
    } catch (Exception e) {
      return Optional.empty();
    }  }


}
