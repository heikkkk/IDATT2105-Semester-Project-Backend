package no.ntnu.idi.idatt2105.quizopia.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDto {

  @JsonProperty("access_token")
  private String accessToken;

  @JsonProperty("access_token_expiry")
  private int accessTokenExpiry;

  @JsonProperty("token_type")
  private TokenType tokenType;

  @JsonProperty("user_name")
  private String username;
}
