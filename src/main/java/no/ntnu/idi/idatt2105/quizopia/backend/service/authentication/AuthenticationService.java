package no.ntnu.idi.idatt2105.quizopia.backend.service.authentication;

import java.util.Arrays;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.UserRegistrationDto;
import no.ntnu.idi.idatt2105.quizopia.backend.model.User;
import no.ntnu.idi.idatt2105.quizopia.backend.service.user.UserMapper;
import no.ntnu.idi.idatt2105.quizopia.backend.config.jwt.JwtTokenGenerator;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.AuthenticationResponseDto;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.TokenType;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc.user.JdbcRoleRepository;
import no.ntnu.idi.idatt2105.quizopia.backend.repository.jdbc.user.JdbcUserRepository;

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
  private final UserMapper userMapper;
  private final JdbcRoleRepository roleRepository;

  /**
   * Generates JWT access tokens for an authenticated user.
   * @param authentication The authentication object containing user details.
   * @return An AuthenticationResponseDto containing the generated tokens.
   */
  public AuthenticationResponseDto getJwtTokens(Authentication authentication) {
    try {
      var user = userRepository.findByName(authentication.getName())
          .orElseThrow(() -> {
            log.error("[AuthService:getJwtTokens] User :{} not found", authentication.getName());
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND");
          });

      String accessToken = jwtTokenGenerator.generateAccessToken(authentication);

      log.info("[AuthenticationService:getJwtTokens] Access token for user:{}, has been generated",
          user.getUsername());
      return AuthenticationResponseDto.builder()
          .accessToken(accessToken)
          .accessTokenExpiry(15 * 60)
          .username(user.getUsername())
          .tokenType(TokenType.Bearer)
          .build();
    } catch (Exception e) {
      log.error("[AuthenticationService:getJwtTokens] Exception while authenticating user due "
          + "to: " +
          e.getMessage());
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Try Again");
    }
  }

  /**
   * Creates an authentication object based on user details retrieved from the database.
   *
   * @param user The user entity.
   * @return The created authentication object.
   */
  private Authentication createAuthenticationObject(User user) {

    String username = user.getUsername();
    String password = user.getPassword();
    String roles = roleRepository.findTypeById(user.getRoleId()).get();

    String[] roleArray = roles.split(",");
    GrantedAuthority[] authorities = Arrays.stream(roleArray)
        .map(role -> (GrantedAuthority) role::trim)
        .toArray(GrantedAuthority[]::new);

    return new UsernamePasswordAuthenticationToken(username, password, Arrays.asList(authorities));
  }

  /**
   * Registers a new user and generates a new JWT token for them.
   *
   * @param userRegistrationDto   The DTO containing user registration information.
   * @return An AuthenticationResponseDto containing the generated tokens.
   * @throws ResponseStatusException If the user already exists or an error occurs during registration.
   */
  public AuthenticationResponseDto registerUser(UserRegistrationDto userRegistrationDto) {
    try {
      Optional<User> user = userRepository.findByName(userRegistrationDto.username());
      if (user.isPresent()) {
        throw new Exception("User Already Exist");
      }

      User userDetails = userMapper.toUser(userRegistrationDto);
      Authentication authentication = createAuthenticationObject(userDetails);

      String accessToken = jwtTokenGenerator.generateAccessToken(authentication);

      userRepository.save(userDetails);

      return AuthenticationResponseDto.builder()
          .accessToken(accessToken)
          .accessTokenExpiry(5 * 60)
          .username(userDetails.getUsername())
          .tokenType(TokenType.Bearer)
          .build();

    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }
}
