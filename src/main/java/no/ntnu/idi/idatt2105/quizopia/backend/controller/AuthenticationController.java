package no.ntnu.idi.idatt2105.quizopia.backend.controller;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.UserRegistrationDto;
import no.ntnu.idi.idatt2105.quizopia.backend.service.authentication.AuthenticationService;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class AuthenticationController {
  private final AuthenticationService authenticationService;

  @PostMapping("/sign-in")
  public ResponseEntity<?> authenticateUser(Authentication authentication, HttpServletResponse response) {
    log.info("[AuthenticationController::authenticateUser] authenticating user: {}",
        authentication.getName());
    return ResponseEntity.ok(
        authenticationService.getJwtTokens(authentication, response)
    );
  }

  @PreAuthorize("hasAuthority('SCOPE_REFRESH_TOKEN')")
  @PostMapping("/refresh-token")
  public ResponseEntity<?> getAccessToken(
      @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
  ) {
    return ResponseEntity.ok(authenticationService.getJwtTokenWithRefreshToken(authorizationHeader));
  }

  @PostMapping("/sign-up")
  public ResponseEntity<?> registerUser(
      @RequestBody UserRegistrationDto userRegistrationDto,
      BindingResult bindingResult,
      HttpServletResponse httpServletResponse
      ) {
    if (bindingResult.hasErrors()) {
      List<String> errorMessage = bindingResult.getAllErrors().stream()
          .map(DefaultMessageSourceResolvable::getDefaultMessage)
          .toList();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
    return ResponseEntity.ok(authenticationService.registerUser(userRegistrationDto,
        httpServletResponse));
  }
}
