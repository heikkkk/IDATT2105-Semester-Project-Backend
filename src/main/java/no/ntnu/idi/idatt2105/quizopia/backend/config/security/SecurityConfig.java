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
   * Security filter for api-requests made to the "sign-in" endpoints. This method is used to
   * generate JWT-tokens when registered users sign in to the application.
   * @param httpSecurity
   * @return JWT-token
   * @throws Exception
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

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withPublicKey(rsaKeyRecord.rsaPublicKey()).build();
  }

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