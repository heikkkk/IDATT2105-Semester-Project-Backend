package no.ntnu.idi.idatt2105.quizopia.backend.config.jwt;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

/**
 * Class for generating JWT access and refresh tokens.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class JwtTokenGenerator {

  private final JwtEncoder jwtEncoder;

  /**
   * Generates a JWT access-token from an {@link Authentication} object.
   * The {@link Authentication} object is used to get the permissions and name of the user.
   * <br> A {@link JwtClaimsSet} is generated and used:
   * <ul>
   * <li>to set the issuer of the token</li>
   * <li>the time at which the token is created</li>
   * <li>the expiry of the token</li>
   * <li>the user associated with the token and the permissions associated with the token</li>
   * </ul>
   * @param authentication The authentication object representing the user's authentication status.
   * @return The generated JWT access token.
   */

  public String generateAccessToken(Authentication authentication) {
    log.info("[JwtTokenGenerator:generateAccessToken] Token Creation Started for :{}",
        authentication.getName());

    String role = getRoleOfUser(authentication);

    String permissions = getPermissionsFromRole(role);

    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("quizopia-group")
        .issuedAt(Instant.now())
        .expiresAt(Instant.now().plus(15, ChronoUnit.MINUTES))
        .subject(authentication.getName())
        .claim("scope", permissions)
        .build();
    return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }

  /**
   * Retrieves the role of the user from the authentication object.
   * @param authentication The authentication object representing the user's authentication status.
   * @return The role of the user.
   */
  private static String getRoleOfUser(Authentication authentication) {
    return authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(" "));
  }

  /**
   * Retrieves the permissions associated with the user's role.
   * @param role The role of the user.
   * @return The permissions associated with the user's role.
   */
  private String getPermissionsFromRole(String role) {
    Set<String> permissions = new HashSet<>();

    if (role.contains("ROLE_ADMIN")) {
      permissions.addAll(List.of("READ", "WRITE", "DELETE"));
    }
    if (role.contains("ROLE_GUEST")) {
      permissions.add("READ");
    }
    if (role.contains("ROLE_USER")) {
      permissions.addAll(List.of("READ", "WRITE"));
    }
    return String.join(" ", permissions);
  }
}
