package no.ntnu.idi.idatt2105.quizopia.backend.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.TokenType;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc.JdbcRefreshTokenRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogoutHandlerService implements LogoutHandler {
  private final JdbcRefreshTokenRepository refreshTokenRepository;


  /**
   * Logs out the user by revoking the refresh token in the database and clearing the refresh token
   * cookie.
   * <br>
   * If the refresh token is not found, sets the response status to 400 BAD REQUEST.
   *
   * @param request        The HTTP servlet request.
   * @param response       The HTTP servlet response.
   * @param authentication The authentication object representing the user's authentication details.
   */
  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) {

    final String authenticationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (!authenticationHeader.startsWith(TokenType.Bearer.name())) {
      return;
    }
    final String refreshToken = authenticationHeader.substring(7);

    var storedRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken)
        .map(token -> {
          refreshTokenRepository.updateIsRevokedByUserId(token.getUser_id());
          return token;
        })
        .orElse(null);

    Cookie emptyReturnCookie = new Cookie("refresh_token","");
    emptyReturnCookie.setMaxAge(0);
    response.addCookie(emptyReturnCookie);

    if (storedRefreshToken == null) {
      response.setStatus(400);
    }
  }
}
