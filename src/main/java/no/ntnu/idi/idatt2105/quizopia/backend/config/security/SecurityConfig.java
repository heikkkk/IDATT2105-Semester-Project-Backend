package no.ntnu.idi.idatt2105.quizopia.backend.config.security;

import static org.springframework.security.config.Customizer.withDefaults;


import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.idatt2105.quizopia.backend.config.RSAKeyRecord;
import no.ntnu.idi.idatt2105.quizopia.backend.config.jwt.JwtRefreshTokenFilter;
import no.ntnu.idi.idatt2105.quizopia.backend.config.jwt.JwtTokenFilter;
import no.ntnu.idi.idatt2105.quizopia.backend.config.jwt.JwtTokenUtils;
import no.ntnu.idi.idatt2105.quizopia.backend.config.user.UserConfigService;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc.JdbcRefreshTokenRepository;
import no.ntnu.idi.idatt2105.quizopia.backend.service.LogoutHandlerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Configure class for security settings of the backend application. Configures authentication
 * and authorization.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

  private final UserConfigService userConfigService;
  private final RSAKeyRecord rsaKeyRecord;
  private final JwtTokenUtils jwtTokenUtils;
  private final JdbcRefreshTokenRepository refreshTokenRepository;
  private final LogoutHandlerService logoutHandlerService;

  /**
   * Security filter made for sign-in endpoints.
   * This chain handles authentication requests for signing in user.
   * @param httpSecurity HttpSecurity object to configure security.
   * @return SecurityFilterChain for sign-in endpoints.
   * @throws Exception If an error occurs during configuration.
   */
  @Bean
  public SecurityFilterChain signInSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .securityMatcher(new AntPathRequestMatcher("/sign-in/**"))
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
        .userDetailsService(userConfigService)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .exceptionHandling(exception -> {
          log.error("[SecurityConfig:signInSecurityFilterChain] Exception due to :{}", exception);
          exception.authenticationEntryPoint((request, response, authException) ->
                  response.sendError(
                      HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage())
              );
            }
        )
        .httpBasic(withDefaults())
        .build();
  }

  /**
   * Security filter made for the api endpoints.
   * This chain handles authentication requests any user may make to the endpoints for the api.
   * @param httpSecurity HttpSecurity object to configure security.
   * @return SecurityFilterChain for api endpoints.
   * @throws Exception If an error occurs during configuration.
   */
  @Bean
  public SecurityFilterChain apiSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .securityMatcher(new AntPathRequestMatcher("/api/**"))
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(
            new JwtTokenFilter(rsaKeyRecord, jwtTokenUtils),
            UsernamePasswordAuthenticationFilter.class
        )
        .exceptionHandling(exception -> {
          log.error("[SecurityConfig:apiSecurityFilterChain] Exception due to :{}", exception);
          exception.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint());
          exception.accessDeniedHandler(new BearerTokenAccessDeniedHandler());
        })
        .httpBasic(withDefaults())
        .build();
  }

  /**
   * Security filter made for the refresh-token endpoints.
   * This chain handles authentication requests for getting new access-tokens to user.
   * @param httpSecurity HttpSecurity object to configure security.
   * @return SecurityFilterChain for refresh-token endpoints.
   * @throws Exception If an error occurs during configuration.
   */
  @Bean SecurityFilterChain refreshTokenSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .securityMatcher(new AntPathRequestMatcher("/refresh-token/**"))
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(new JwtRefreshTokenFilter(
            rsaKeyRecord,
            jwtTokenUtils,
            refreshTokenRepository),
            UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(exception -> {
              log.error("[SecurityConfig:refreshTokenSecurity] Exception due to :{}", exception);
              exception.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint());
              exception.accessDeniedHandler(new BearerTokenAccessDeniedHandler());
        })
        .httpBasic(withDefaults())
        .build();
  }

  /**
   * Security filter made for the logout endpoint.
   * This chain handles authentication of logout requests. It passes the logout request to the
   * {@link  LogoutHandlerService}, which ensures that any refresh-token stored in the database is
   * invalidated.
   * @param httpSecurity HttpSecurity object to configure security.
   * @return SecurityFilterChain for refresh-token endpoints.
   * @throws Exception If an error occurs during configuration.
   */
  @Bean SecurityFilterChain logoutSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .securityMatcher(new AntPathRequestMatcher("/logout/**"))
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(new JwtTokenFilter(rsaKeyRecord, jwtTokenUtils),
            UsernamePasswordAuthenticationFilter.class)
        .logout(logout -> {
              logout
                  .logoutUrl("/logout")
                  .addLogoutHandler(logoutHandlerService)
                  .logoutSuccessHandler(((request, response, authentication) ->
                      SecurityContextHolder.getContext()));
              log.info("[SecurityConfig:logoutSecurityFilterChain] User logged out");
            }
        )
        .exceptionHandling(exception -> {
          log.error("[SecurityConfig:logoutSecurityFilterChain] Exception due to :{}", exception);
          exception.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint());
          exception.accessDeniedHandler(new BearerTokenAccessDeniedHandler());
        })
        .build();
  }

  /**
   * Configures security filter chain for registration endpoints.
   * This chain allows unauthenticated access to registration endpoints.
   * @param httpSecurity HttpSecurity object to configure security.
   * @return SecurityFilterChain for registration endpoints.
   * @throws Exception If an error occurs during configuration.
   */
  @Bean
  public SecurityFilterChain registerSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .securityMatcher(new AntPathRequestMatcher("/sign-up/**"))
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth ->
            auth.anyRequest().permitAll())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .build();
  }

  /**
   * Creates a BCrypt password encoder bean.
   * This encoder is used for securely hashing passwords.
   * @return BCryptPasswordEncoder bean.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Creates a JwtDecoder bean.
   * This decoder is used for validating JWT tokens.
   * @return JwtDecoder bean.
   */
  @Bean
  JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withPublicKey(rsaKeyRecord.rsaPublicKey()).build();
  }

  /**
   * Creates a JwtEncoder bean.
   * This encoder is used for generating JWT tokens.
   * @return JwtEncoder bean.
   */
  @Bean
  JwtEncoder jwtEncoder() {
    JWK jwk = new RSAKey.Builder(
        rsaKeyRecord.rsaPublicKey()).
        privateKey(rsaKeyRecord.rsaPrivateKey())
        .build();
    JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
    return new NimbusJwtEncoder(jwkSource);
  }
}
