package no.ntnu.idi.idatt2105.quizopia.backend.JDBCrepository;

import java.util.List;
import java.util.Optional;
import no.ntnu.idi.idatt2105.quizopia.backend.model.User;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.UserRepository;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class JDBCUserRepository implements UserRepository {

  private final JdbcTemplate jdbcTemplate;

  public JDBCUserRepository(JdbcTemplate jdbcTemplate) {
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
    return jdbcTemplate.update("DELET");
  }

  @Override
  public Optional<User> findById(Long userId) {
    String sql = "SELECT * FROM users WHERE user_id=?";
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
}
