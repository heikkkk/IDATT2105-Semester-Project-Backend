package no.ntnu.idi.idatt2105.quizopia.backend.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.idatt2105.quizopia.backend.config.RSAKeyRecord;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc.JdbcRefreshTokenRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Slf4j
public class JwtRefreshTokenFilter extends OncePerRequestFilter {
  private final RSAKeyRecord rsaKeyRecord;
  private final JwtTokenUtils jwtTokenUtils;
  private final JdbcRefreshTokenRepository refreshTokenRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      log.info("[JwtRefreshTokenFilter::doFilterInternal]Filtering Http request: {}",
          request.getRequestURI());

      final String authenticationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

      JwtDecoder jwtDecoder = NimbusJwtDecoder.withPublicKey(rsaKeyRecord.rsaPublicKey()).build();

      if (!authenticationHeader.startsWith("Bearer ")) {
        filterChain.doFilter(request, response);
        return;
      }

      final String token = authenticationHeader.substring(7);
      final Jwt jwtRefreshToken = jwtDecoder.decode(token);

      final String username = jwtTokenUtils.getUsername(jwtRefreshToken);

      if (!username.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
        var refreshTokenValidInDatabase =
            refreshTokenRepository.findByRefreshToken(jwtRefreshToken.getTokenValue())
                .map(refreshToken -> !refreshToken.isRevoked())
                .orElse(false);

        UserDetails userDetails = jwtTokenUtils.userDetails(username);
        if (jwtTokenUtils.tokenIsValid(jwtRefreshToken, userDetails) && refreshTokenValidInDatabase) {
          SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

          UsernamePasswordAuthenticationToken createdToken = new UsernamePasswordAuthenticationToken(
              userDetails,
              null,
              userDetails.getAuthorities()
          );

          createdToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          securityContext.setAuthentication(createdToken);
          SecurityContextHolder.setContext(securityContext);
        }

      }
      log.info("[JwtRefreshTokenFilter::doFilterInternal]Complete");
      filterChain.doFilter(request, response);
    } catch (JwtValidationException jwtValidationException) {
      log.info("[JwtRefreshTokenFilter::doFilterInternal]Exception due to: {}",
          jwtValidationException.getMessage());
      throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
          jwtValidationException.getMessage());
    }
  }
}
