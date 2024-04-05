package no.ntnu.idi.idatt2105.quizopia.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.AuthenticationResponseDto;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.UserRegistrationDto;
import no.ntnu.idi.idatt2105.quizopia.backend.service.authentication.AuthenticationService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

  @Operation(
      summary = "Sign in to application",
      description = "Sign in to the application by providing a BASE64 encoded username and "
          + "password in the authorization header"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Successfully sign into the application, with the correct authorization",
          content = {
              @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AuthenticationResponseDto.class)
              )
          },
          headers = {
              @Header(name = "Set-Cookie", description = "HttpOnly cookie which includes a "
                  + "refresh token ")
          }
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Incorrect authentication details",
          content = @Content
      )
  })
  @Parameter(
      description = "Basic Authentication header",
      required = true,
      example = "Basic YXNkZnNhZGZzYWQ=")
  @PostMapping("/sign-in")
  public ResponseEntity<?> authenticateUser(
      Authentication authentication, HttpServletResponse response) {
    log.info("[AuthenticationController::authenticateUser] authenticating user: {}",
        authentication.getName());
    return ResponseEntity.ok(
        authenticationService.getJwtTokens(authentication, response)
    );
  }

  @Operation(
      summary = "Refresh Access Token",
      description = "Get a new access token using a refresh token"
  )
  @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200",
        description = "Successfully get a new access token",
        content = {
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AuthenticationResponseDto.class)
            )
        },
        headers = {
            @Header(name = "Set-Cookie", description = "HttpOnly cookie which includes a "
                + "refresh token ")
        }
        ),
      @ApiResponse(
          responseCode = "400",
          description = "Invalid authentication header, or refresh token is revoked",
          content = @Content
      ),
  })
  @Parameter(
      description = "Authorization header in the format 'Bearer [token]'",
      required = true,
      example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  )
  @PreAuthorize("hasAuthority('SCOPE_REFRESH_TOKEN')")
  @PostMapping("/refresh-token")
  public ResponseEntity<?> getAccessToken(
      @RequestHeader(HttpHeaders.AUTHORIZATION)
      String authorizationHeader) {
    return ResponseEntity.ok(authenticationService.getJwtTokenWithRefreshToken(authorizationHeader));
  }

  @Operation(
      summary = "Register a user",
      description = "Sign up a user to the application by providing username, password, email and"
          + " roleId"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Successfully registred a user to the application",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = AuthenticationResponseDto.class)
              )
          }
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Invalid content of request body. Does not match UserRegistrationDto",
          content = @Content
      )
  })
  @Parameter(
      description = "User information including username, password, email and roleId",
      schema = @Schema(implementation = UserRegistrationDto.class)
  )
  @PostMapping("/sign-up")
  public ResponseEntity<?> registerUser(
      @RequestBody UserRegistrationDto userRegistrationDto,
      HttpServletResponse httpServletResponse
      ) {
    return ResponseEntity.ok(authenticationService.registerUser(userRegistrationDto,
        httpServletResponse));
  }
}
