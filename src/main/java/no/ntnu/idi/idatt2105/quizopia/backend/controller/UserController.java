package no.ntnu.idi.idatt2105.quizopia.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.idatt2105.quizopia.backend.dto.UsernameRequestDto;
import no.ntnu.idi.idatt2105.quizopia.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/get-name/{userId}")
  public ResponseEntity<String> getUsernameById(@PathVariable Long userId) {
    return ResponseEntity.ok(userService.findUsernameById(userId));
  }

  @PostMapping("/get-id")
  public ResponseEntity<Long> getIdByUsername(@RequestBody UsernameRequestDto username) {
    return ResponseEntity.ok(userService.findIdByUsername(username.username()));
  }
}
