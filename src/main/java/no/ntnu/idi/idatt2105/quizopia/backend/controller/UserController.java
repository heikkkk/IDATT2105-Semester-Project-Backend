package no.ntnu.idi.idatt2105.quizopia.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.idatt2105.quizopia.backend.service.user.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;


  @Operation(
      summary = "Get name by id",
      description = "Get the username of a user by providing the corresponding user-id as the "
          + "path variable"
  )
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
  @Parameter(
      description = "Id of user to be searched",
      required = true,
      example = "1"
  )
  @GetMapping("/get-name/{userId}")
  public ResponseEntity<String> getUsernameById(@PathVariable Long userId) {
    return ResponseEntity.ok(userService.findUsernameById(userId));
  }

  @Operation(
      summary = "Get id by username",
      description = "Get the id of a user by providing the corresponding username as the path "
          + "variable"
  )
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
  @Parameter(
      description = "Name of user to be searched",
      required = true,
      example = "adminUser"
  )
  @GetMapping("/get-id/{username}")
  public ResponseEntity<Long> getIdByUsername(@PathVariable String username) {
    return ResponseEntity.ok(userService.findIdByUsername(username));
  }

  // TODO ADD SWAGGER
  @PutMapping("/update-password/{userId}/{newPassword}")
  public ResponseEntity<Void> putUpdatePassword(@PathVariable Long userId, @PathVariable String newPassword) {
    boolean passwordSetSuccessfully = userService.updatePassword(userId, newPassword);
    if (passwordSetSuccessfully) {
        return ResponseEntity.ok().build(); 
    } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); 
    }
  }
}
