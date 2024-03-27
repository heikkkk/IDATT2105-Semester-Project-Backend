package no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import no.ntnu.idi.idatt2105.quizopia.backend.model.RefreshToken;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.RefreshTokenRepository;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JdbcRefreshTokenRepository implements RefreshTokenRepository {

  private final JdbcTemplate jdbcTemplate;


  @Override
  public int save(RefreshToken refreshToken) {
    String sql = "INSERT INTO refresh_token (refreshToken, revoked, user_id) VALUES (?,?,?)";
    return jdbcTemplate.update(sql, refreshToken.getRefreshToken(), refreshToken.isRevoked(),
        refreshToken.getUser_id());
  }

  @Override
  public int delete(Long tokenId) {
    String sql = "DELETE FROM refresh_token WHERE id=?";
    return jdbcTemplate.update(sql, tokenId);
  }

  @Override
  public Optional<List<RefreshToken>> findByUserId(Long user_id) {
    String sql = "SELECT * FROM refresh_token WHERE user_id=?";
    try {
      List<RefreshToken> refreshTokens = jdbcTemplate.query(
          sql,
          new BeanPropertyRowMapper<>(RefreshToken.class),
          user_id);
      return Optional.of(refreshTokens);
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<RefreshToken> findByRefreshToken(String refreshToken) {
    String sql = "SELECT * FROM refresh_token WHERE refreshtoken=?";
    try {
      RefreshToken token = jdbcTemplate.queryForObject(
          sql,
          BeanPropertyRowMapper.newInstance(RefreshToken.class),
          refreshToken);
      return Optional.ofNullable(token);
    } catch (IncorrectResultSizeDataAccessException e) {
      return Optional.empty();
    }
  }

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

  @Override
  public int updateRefreshToken(Long refreshTokenId, String refreshToken) {
    String sql = "UPDATE refresh_token SET refreshtoken=? WHERE id=?";
    return jdbcTemplate.update(sql, refreshToken, refreshTokenId);
  }

  @Override
  public int saveAll(List<RefreshToken> refreshTokens) {
    int numAffected = 0;
    for (RefreshToken token : refreshTokens) {
      numAffected += save(token);
    }
    return numAffected;
  }

  @Override
  public List<RefreshToken> findAllByUsername(String username) {
    String sql = "SELECT rt.* FROM refresh_token rt INNER JOIN users ud on rt.user_id = ud.id "
        + "WHERE ud.username =? and rt.revoked = false";
    return jdbcTemplate.query(
        sql,
        new BeanPropertyRowMapper<>(RefreshToken.class),
        username
    );
  }
}

