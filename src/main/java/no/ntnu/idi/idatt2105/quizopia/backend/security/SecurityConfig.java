package no.ntnu.idi.idatt2105.quizopia.backend.security;

import static org.springframework.security.config.Customizer.withDefaults;

import jakarta.servlet.http.HttpServletResponse;
import no.ntnu.idi.idatt2105.quizopia.backend.config.user.UserConfigService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  private final UserConfigService userConfigService;

  public SecurityConfig(UserConfigService userConfigService) {
    this.userConfigService = userConfigService;
  }

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
          exception.authenticationEntryPoint((request, response, authException) ->
              response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage()));
        })
        .httpBasic(withDefaults())
        .build();
  }

  @Bean
  public SecurityFilterChain apiSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .securityMatcher(new AntPathRequestMatcher("/api/**"))
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
        .userDetailsService(userConfigService)
        .httpBasic(withDefaults())
        .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
