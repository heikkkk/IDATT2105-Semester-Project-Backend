package no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.idatt2105.quizopia.backend.model.RefreshToken;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.RefreshTokenRepository;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Slf4j
@Repository
public class JdbcRefreshTokenRepository implements RefreshTokenRepository {

  private final JdbcTemplate jdbcTemplate;

  /**
   * Save a {@link RefreshToken} to the database
   * @param refreshToken The {@link RefreshToken} to be saved
   * @return the number of rows affected
   */
  @Override
  public int save(RefreshToken refreshToken) {
    String sql = "INSERT INTO refresh_token (refreshToken, revoked, user_id) VALUES (?,?,?)";
    return jdbcTemplate.update(sql, refreshToken.getRefreshToken(), refreshToken.isRevoked(),
        refreshToken.getUserId());
  }

  /**
   * Delete a {@link RefreshToken} from the database given the ID
   * @param tokenId the ID of the {@link RefreshToken}
   * @return the number of rows affected
   */
  @Override
  public int delete(Long tokenId) {
    String sql = "DELETE FROM refresh_token WHERE refresh_token_id=?";
    return jdbcTemplate.update(sql, tokenId);
  }

  /**
   * Find a {@link List} of {@link RefreshToken RefreshTokens} given the ID of the
   * {@link no.ntnu.idi.idatt2105.quizopia.backend.model.User User} associated with the token
   * @param userId the ID of the {@link no.ntnu.idi.idatt2105.quizopia.backend.model.User User}
   * @return the {@link List} of {@link RefreshToken RefreshTokens} if any exists
   */
  @Override
  public Optional<List<RefreshToken>> findByUserId(Long userId) {
    String sql = "SELECT * FROM refresh_token WHERE user_id=?";
    try {
      List<RefreshToken> refreshTokens = jdbcTemplate.query(
          sql,
          new BeanPropertyRowMapper<>(RefreshToken.class),
          userId);
      return Optional.of(refreshTokens);
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  /**
   * Find a {@link RefreshToken} in the database given the value of the token
   * @param refreshToken The value of the {@link RefreshToken}
   * @return the {@link RefreshToken} if it exists
   */
  @Override
  public Optional<RefreshToken> findByRefreshToken(String refreshToken) {
    String sql = "SELECT * FROM refresh_token WHERE refreshtoken=?";
    try {
      RefreshToken token = jdbcTemplate.queryForObject(
          sql,
          BeanPropertyRowMapper.newInstance(RefreshToken.class),
          refreshToken);
      return Optional.of(token);
    } catch (IncorrectResultSizeDataAccessException e) {
      log.error("[JdbcRefreshTokenRepository:findByRefreshToken] error: {}",e.getMessage());
      return Optional.empty();
    }
  }

  /**
   * Find all {@link RefreshToken RefreshTokens} in the database
   * @return a {@link List} of {@link RefreshToken RefreshTokens} if any exists
   */
  @Override
  public Optional<List<RefreshToken>> findAll() {
    String sql = "SELECT * FROM refresh_token";
    try {
      List<RefreshToken> refreshTokens = jdbcTemplate.query(
          sql,
          new BeanPropertyRowMapper<>(RefreshToken.class));
      return Optional.of(refreshTokens);
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  /**
   * Update the {@link RefreshToken} given the ID
   * @param refreshTokenId  The ID of the {@link RefreshToken} to be updated
   * @param refreshToken    The new value of the {@link RefreshToken}
   * @return the number of rows affected
   */
  @Override
  public int updateRefreshToken(Long refreshTokenId, String refreshToken) {
    String sql = "UPDATE refresh_token SET refreshtoken=? WHERE refresh_token_id=?";
    return jdbcTemplate.update(sql, refreshToken, refreshTokenId);
  }

  /**
   * Save a {@link List} of {@link RefreshToken RefreshTokens} to the database
   * @param refreshTokens The list of {@link RefreshToken RefreshTokens}
   * @return the number of rows affected
   */
  @Override
  public int saveAll(List<RefreshToken> refreshTokens) {
    int numAffected = 0;
    for (RefreshToken token : refreshTokens) {
      numAffected += save(token);
    }
    return numAffected;
  }

  /**
   * Find all {@link RefreshToken RefreshTokens} associated with a username
   * @param username The username with the associated {@link RefreshToken RefreshTokens}
   * @return a {@link List} of {@link RefreshToken RefreshTokens}
   */
  @Override
  public List<RefreshToken> findAllByUsername(String username) {
    String sql = "SELECT rt.* FROM refresh_token rt INNER JOIN user u on rt.user_id = u.user_id "
        + "WHERE u.username =? and rt.revoked = false";
    return jdbcTemplate.query(
        sql,
        new BeanPropertyRowMapper<>(RefreshToken.class),
        username
    );
  }

  /**
   * Update the revoked field of a {@link RefreshToken} in the database given the ID of the
   * {@link no.ntnu.idi.idatt2105.quizopia.backend.model.User User} associated with the token
   * @param userId the ID of the {@link no.ntnu.idi.idatt2105.quizopia.backend.model.User User}
   * @return the number of rows affected
   */
  @Override
  public int updateIsRevokedByUserId(Long userId){
    String sql = "UPDATE refresh_token rt SET rt.revoked=true WHERE rt.user_id=?";
    return jdbcTemplate.update(
        sql,
        userId
    );
  }

}

