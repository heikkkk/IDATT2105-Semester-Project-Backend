package no.ntnu.idi.idatt2105.quizopia.backend.repository;

import java.util.List;
import java.util.Optional;
import no.ntnu.idi.idatt2105.quizopia.backend.model.RefreshToken;

public interface RefreshTokenRepository {

  int save(RefreshToken refreshToken);

  int delete(Long tokenId);

  Optional<List<RefreshToken>> findByUserId(Long user_id);

  Optional<RefreshToken> findByRefreshToken(String refreshToken);

  Optional<List<RefreshToken>> findAll();

  int updateRefreshToken(Long refreshTokenId, String refreshToken);

  int saveAll(List<RefreshToken> refreshTokens);

  List<RefreshToken> findAllByUsername(String username);

  int updateIsRevokedByUserId(Long userId);
}
