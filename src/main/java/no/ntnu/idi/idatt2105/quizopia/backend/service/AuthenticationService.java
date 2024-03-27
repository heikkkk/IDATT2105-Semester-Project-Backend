package no.ntnu.idi.idatt2105.quizopia.backend.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.idatt2105.quizopia.backend.model.RefreshToken;
import no.ntnu.idi.idatt2105.quizopia.backend.model.User;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc.JdbcUserRepository;
import no.ntnu.idi.idatt2105.quizopia.backend.config.jwt.JwtTokenGenerator;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.AuthenticationResponseDto;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.TokenType;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc.JdbcRefreshTokenRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
  private final JdbcUserRepository userRepository;
  private final JwtTokenGenerator jwtTokenGenerator;
  private final JdbcRefreshTokenRepository refreshTokenRepository;

  /**
   * Get the JWT access token for an authenticated user.
   * @param authentication the details of user that requested the JWT-token
   * @return AuthenticationResponseDto containing the JWT-token
   */
  public AuthenticationResponseDto getJwtTokens(Authentication authentication,
      HttpServletResponse response) {
    try {
      var user = userRepository.findByName(authentication.getName())
          .orElseThrow(() -> {
            log.error("[AuthService:userSignInAuth] User :{} not found", authentication.getName());
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND");
          });

      String accessToken = jwtTokenGenerator.generateAccessToken(authentication);
      String refreshToken = jwtTokenGenerator.generateRefreshToken(authentication);

      createRefreshTokenCookie(response, refreshToken);

      saveUserRefreshToken(user, refreshToken);
      log.info("[AuthenticationService:userSignInAuth] Access token for user:{}, has been generated",
          user.getUsername());
      return AuthenticationResponseDto.builder()
          .accessToken(accessToken)
          .accessTokenExpiry(15 * 60)
          .username(user.getUsername())
          .tokenType(TokenType.Bearer)
          .build();

      //refresh token

    } catch (Exception e) {
      log.error("[AuthenticationService:userSignInAuth] Exception while authenticating user due "
          + "to: " +
          e.getMessage());
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Try Again");
    }
  }

  private Cookie createRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
    Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
    refreshTokenCookie.setHttpOnly(true);
    refreshTokenCookie.setSecure(true);
    refreshTokenCookie.setMaxAge(15 * 24 * 60 * 60); // 15 days
    response.addCookie(refreshTokenCookie);
    return refreshTokenCookie;
  }


  private void saveUserRefreshToken(User user, String token) {
    var refreshToken = RefreshToken.builder()
        .user_id(userRepository.findIdByName(user.getUsername()).get())
        .refreshToken(token)
        .revoked(false)
        .build();
    refreshTokenRepository.save(refreshToken);
  }


  public AuthenticationResponseDto getJwtTokenWithRefreshToken(String authorizationHeader)
      throws ResponseStatusException{
    if (!authorizationHeader.startsWith(TokenType.Bearer.name())) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Verify token type");
    }

    final String refreshToken = authorizationHeader.substring(7);

    var token = refreshTokenRepository.findByRefreshToken(refreshToken)
        .filter(tokens-> !tokens.isRevoked())
        .orElseThrow(()-> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Refesh "
            + "token revoked"));

    User user = userRepository.findById(token.getUser_id()).get();

    Authentication authentication = createAuthenticationObject(user);

    String accessToken = jwtTokenGenerator.generateAccessToken(authentication);

    return AuthenticationResponseDto.builder()
        .accessToken(accessToken)
        .accessTokenExpiry(5*60)
        .username(user.getUsername())
        .tokenType(TokenType.Bearer)
        .build();
  }

  private Authentication createAuthenticationObject(User user) {

    String username = user.getUsername();
    String password = user.getPassword();
    String roles = userRepository.findRoleByName(username).get();

    String[] roleArray = roles.split(",");
    GrantedAuthority[] authorities = Arrays.stream(roleArray)
        .map(role -> (GrantedAuthority) role::trim)
        .toArray(GrantedAuthority[]::new);

    return new UsernamePasswordAuthenticationToken(username, password, Arrays.asList(authorities));
  }
}
