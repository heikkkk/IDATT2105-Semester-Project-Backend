package no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc;

import java.util.List;
import java.util.Optional;
import no.ntnu.idi.idatt2105.quizopia.backend.model.Role;
import no.ntnu.idi.idatt2105.quizopia.backend.model.User;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.UserRepository;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Repository class for interacting with the database table storing user information.
 */
@Repository
public class JdbcUserRepository implements UserRepository {

  private final JdbcTemplate jdbcTemplate;

  /**
   * Constructs a JdbcUserRepository with the provided JdbcTemplate.
   *
   * @param jdbcTemplate the JdbcTemplate to be used for database operations.
   */
  public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  /**
   * Saves a {@link User} to the database
   * @param user The user to be saved
   * @return the number of rows affected
   */
  @Override
  public int save(User user) {
    String sql = "INSERT INTO user (username, password, email, role_id) VALUES(?,?,?,?)";
    return jdbcTemplate.update(
        sql,
        user.getUsername(), user.getPassword(), user.getEmail(), user.getRoleId());
  }

  /**
   * Delete a {@link User} from the database
   * @param userId ID of the user to be deleted
   * @return the number of rows affected
   */
  @Override
  public int delete(Long userId) {
    return jdbcTemplate.update("DELETE FROM user WHERE user_id=?", userId);
  }

  /**
   * Find a {@link User} from the database given the ID
   * @param userId The ID of the user to be found
   * @return the user if it exists
   */
  @Override
  public Optional<User> findById(Long userId) {
    String sql = "SELECT * FROM user WHERE user_id=?";
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

  /**
   * Find a {@link User} from the database given the username
   * @param username the username of the user to be found
   * @return the {@link User} if it exists
   */
  @Override
  public Optional<User> findByName(String username) {
    String sql = "SELECT * FROM user WHERE username=?";
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

  /**
   * Find a {@link User} from the databse given the email
   * @param email The email of the user to be found
   * @return the {@link User} if it exists
   */
  @Override
  public Optional<User> findByEmail(String email) {
    String sql = "SELECT * FROM user WHERE email=?";
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

  /**
   * Find all the user in the database
   * @return a {@link List<User>} of {@link User Users} if any exists in the database
   */
  @Override
  public Optional<List<User>> findAll() {
    String sql = "SELECT * FROM user";
    try {
      List<User> users = jdbcTemplate.query(
          sql,
          new BeanPropertyRowMapper<>(User.class));
      return Optional.of(users);
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  /**
   * Find the role of a {@link User} in the database given the ID
   * @param userId The ID of the {@link User}
   * @return the role of the {@link User}, if the {@link User} exists
   */
  @Override
  public Optional<String> findRoleById(Long userId) {
    String sql = "SELECT * FROM role r JOIN user u ON r.role_id = u.role_id WHERE u.user_id=?";
    try {
      Role role = jdbcTemplate.queryForObject(
          sql,
          BeanPropertyRowMapper.newInstance(Role.class),
          userId
          );
      return Optional.of(role.getType());
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  /**
   * Find the role of a {@link User} in the database given the name
   * @param username The username of the {@link User}
   * @return the role of the {@link User}, if the {@link User} exists
   */
  @Override
  public Optional<String> findRoleByName(String username) {
    String sql = "SELECT * FROM role r JOIN user u ON r.role_id = u.role_id WHERE u.username=?";
    try {
      Role role = jdbcTemplate.queryForObject(
          sql,
          BeanPropertyRowMapper.newInstance(Role.class),
          username
      );
      return Optional.of(role.getType());
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  /**
   * Update the username of a {@link User} in the database given the ID
   * @param userId    The ID of the {@link User}
   * @param username  The new username of the user
   * @return the number of rows affected
   */
  @Override
  public int updateUsername(Long userId, String username) {
    return jdbcTemplate.update("UPDATE user SET username=? WHERE user_id=?", username, userId);
  }

  /**
   * Update the password of a {@link User} in the database given the ID
   * @param userId    The ID of the {@link User}
   * @param password  The new password of the {@link User}
   * @return the number of rows affected
   */
  @Override
  public int updatePassword(Long userId, String password) {
    return jdbcTemplate.update("UPDATE user SET password=? WHERE user_id=?", password, userId);
  }

  /**
   * Update the email of a {@link User} in the database given the ID
   * @param userId  The ID of the {@link User}
   * @param email   The new email of the {@link User}
   * @return the number of rows affected
   */
  @Override
  public int updateEmail(Long userId, String email) {
    return jdbcTemplate.update("UPDATE user SET email=? WHERE user_id=?", email, userId);
  }

  /**
   * Save a {@link List} of {@link User user} to the database
   * @param user The list of user
   * @return the number of rows affected
   */
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
    String sql = "SELECT user_id FROM user WHERE username=?";
    try {
      Long id = jdbcTemplate.queryForObject(
          sql,
          new Object[]{username},
          (rs, rowNum) -> rs.getLong("user_id")
      );
      return Optional.of(id);
    } catch (Exception e) {
      return Optional.empty();
    }  }


}
