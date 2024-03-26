package no.ntnu.idi.idatt2105.quizopia.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.idatt2105.quizopia.backend.JDBCrepository.JDBCUserRepository;
import no.ntnu.idi.idatt2105.quizopia.backend.config.jwt.JwtTokenGenerator;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.AuthenticationResponseDto;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.TokenType;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
  private final JDBCUserRepository userRepository;
  private final JwtTokenGenerator jwtTokenGenerator;

  /**
   * Get the JWT access token for an authenticated user.
   * @param authentication the details of user that requested the JWT-token
   * @return AuthenticationResponseDto containing the JWT-token
   */
  public AuthenticationResponseDto getJwtTokens(Authentication authentication) {
    try {
      var user = userRepository.findByName(authentication.getName())
          .orElseThrow(() -> {
            log.error("[AuthService:userSignInAuth] User :{} not found", authentication.getName());
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND");
          });

      String accessToken = jwtTokenGenerator.generateAccessToken(authentication);
      log.info("[AuthService:userSignInAuth] Access token for user:{}, has been generated",
          user.getUsername());
      return AuthenticationResponseDto.builder()
          .accessToken(accessToken)
          .accessTokenExpiry(15 * 60)
          .username(user.getUsername())
          .tokenType(TokenType.Bearer)
          .build();
    } catch (Exception e) {
      log.error("[AuthService:userSignInAuth] Exception while authenticating user due to: " +
          e.getMessage());
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Try Again");
    }
  }
}
