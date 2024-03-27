package no.ntnu.idi.idatt2105.quizopia.backend.service;

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
          token.setRevoked(true);
          refreshTokenRepository.save(token);
          return token;
        })
        .orElse(null);
  }
}
