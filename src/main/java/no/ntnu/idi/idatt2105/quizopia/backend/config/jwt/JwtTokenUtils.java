package no.ntnu.idi.idatt2105.quizopia.backend.config.jwt;

import java.time.Instant;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc.JdbcUserRepository;
import no.ntnu.idi.idatt2105.quizopia.backend.config.user.UserConfig;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenUtils {

  public String getUsername(Jwt token) {
    return token.getSubject();
  }

  public boolean tokenIsValid(Jwt token, UserDetails userDetails) {
    final String username = getUsername(token);
    boolean isTokenExpired = isTokenExpired(token);
    boolean tokenBelongsToUser = username.equals(userDetails.getUsername());
    return !isTokenExpired && tokenBelongsToUser;
  }

  private boolean isTokenExpired(Jwt token) {
    return Objects.requireNonNull(token.getExpiresAt()).isBefore(Instant.now());
  }

  private final JdbcUserRepository userRepository;
  public UserDetails userDetails(String username) {
    return userRepository
        .findByName(username)
        .map(user -> new UserConfig(user, userRepository))
        .orElseThrow(()-> new UsernameNotFoundException("Username: " + username + " does not "
            + "exist"));
  }
}
