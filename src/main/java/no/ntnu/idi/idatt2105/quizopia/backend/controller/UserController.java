package no.ntnu.idi.idatt2105.quizopia.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.idatt2105.quizopia.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;


  @Operation(summary = "Get the name of a user by their id")
  @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200",
        description = "Found the user",
        content = {@Content(mediaType = "text/plain",
          schema = @Schema(implementation = String.class))}
      ),
      @ApiResponse(
        responseCode = "400",
        description = "Invalid id supplied",
        content = @Content
      )
  })
  @GetMapping("/get-name/{userId}")
  public ResponseEntity<String> getUsernameById(
      @Parameter(description = "Id of user to be searched", example = "1")
      @PathVariable Long userId
    ) {
    return ResponseEntity.ok(userService.findUsernameById(userId));
  }

  @Operation(summary = "Get the id of a user by their username")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Found the user",
          content = {@Content(mediaType = "text/plain",
              schema = @Schema(implementation = String.class))}
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Invalid username supplied",
          content = @Content
      )
  })
  @GetMapping("/get-id/{username}")
  public ResponseEntity<Long> getIdByUsername(
      @Parameter(description = "Name of user to be searched", example = "adminUser")
      @PathVariable String username
    ) {
    return ResponseEntity.ok(userService.findIdByUsername(username));
  }
}
