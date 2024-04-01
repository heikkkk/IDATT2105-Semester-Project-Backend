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

/**
 * Utility class for JWT token operations.
 */
@Component
@RequiredArgsConstructor
public class JwtTokenUtils {
  private final JdbcUserRepository userRepository;

  /**
   * Retrieves the username from the JWT token.
   * @param token The JWT token.
   * @return The username extracted from the token.
   */
  public String getUsername(Jwt token) {
    return token.getSubject();
  }

  /**
   * Checks if the JWT token is valid.
   * @param token The JWT token.
   * @param userDetails The UserDetails object representing the user.
   * @return True if the token is valid, false otherwise.
   */
  public boolean tokenIsValid(Jwt token, UserDetails userDetails) {
    final String username = getUsername(token);
    boolean isTokenExpired = isTokenExpired(token);
    boolean tokenBelongsToUser = username.equals(userDetails.getUsername());
    return !isTokenExpired && tokenBelongsToUser;
  }

  /**
   * Loads a user from the userRepository given the username.
   * The method then configures the user to be used by the API by mapping the retrived user with
   * the {@link UserConfig} class
   * @param username the name of the user to be loaded
   * @return the {@link UserDetails} mapped to the user
   * @throws UsernameNotFoundException if the user with the given username is not found.
   */
  public UserDetails userDetails(String username) {
    return userRepository
        .findByName(username)
        .map(user -> new UserConfig(user, userRepository))
        .orElseThrow(()-> new UsernameNotFoundException("Username: " + username + " does not "
            + "exist"));
  }

  /**
   * Checks if the JWT token has expired.
   * @param token The JWT token.
   * @return True if the token has expired, false otherwise.
   */
  private boolean isTokenExpired(Jwt token) {
    return Objects.requireNonNull(token.getExpiresAt()).isBefore(Instant.now());
  }

}
