package no.ntnu.idi.idatt2105.quizopia.backend.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.idatt2105.quizopia.backend.config.RSAKeyRecord;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.TokenType;
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

/**
 * Filter to intercept and process JWT tokens in HTTP requests.
 */
@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

  private final RSAKeyRecord rsaKeyRecord;
  private final JwtTokenUtils jwtTokenUtils;

  /**
   * Filters incoming HTTP requests, intercepts JWT tokens, and processes them.
   * The method first checks the Authorization header of the request to determine if it contains a JWT token.
   * <br>
   * If it does, the JWT token is extracted and decoded using the provided RSA public key.
   * <br>
   * Then, the username is extracted from the decoded JWT token.
   * <br>
   * If the username is not empty and there is no existing authentication in the
   * {@link SecurityContext},
   * {@link UserDetails} are retrieved for the username, and the token's validity is checked.
   * <br>
   * If the token is valid, a new authentication token is created and set in the
   * {@link SecurityContext}.
   * <br>
   * Finally, the filter chain continues to process the request.
   * <br>
   * If JWT validation fails, a NOT_ACCEPTABLE status response is thrown.
   *
   * @param request The HTTP servlet request.
   * @param response The HTTP servlet response.
   * @param filterChain The filter chain.
   * @throws ServletException If a servlet-related exception occurs.
   * @throws IOException If an I/O exception occurs.
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

      log.info("[JwtTokenFilter:doFilterInterval] authHeader: {}", authHeader);

      JwtDecoder jwtDecoder = NimbusJwtDecoder.withPublicKey(rsaKeyRecord.rsaPublicKey()).build();

      if (!authHeader.startsWith(TokenType.Bearer.name())) {
        filterChain.doFilter(request, response);
        return;
      }

      final String token = authHeader.substring(7);
      final Jwt jwtToken = jwtDecoder.decode(token);

      log.info("[JwtTokenFilter:doFilterInterval] jwtToken: {}", jwtToken);

      final String username = jwtTokenUtils.getUsername(jwtToken);

      if (!username.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = jwtTokenUtils.userDetails(username);
        if (jwtTokenUtils.tokenIsValid(jwtToken, userDetails)) {
          SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
          UsernamePasswordAuthenticationToken createdToken =
              new UsernamePasswordAuthenticationToken(
                  userDetails,
                  null,
                  userDetails.getAuthorities()
          );
          createdToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          securityContext.setAuthentication(createdToken);
          SecurityContextHolder.setContext(securityContext);
        }
      }

      filterChain.doFilter(request, response);
    } catch (JwtValidationException jwtValidationException) {
      throw new ResponseStatusException(
          HttpStatus.NOT_ACCEPTABLE, jwtValidationException.getMessage()
      );
    }
  }
}
