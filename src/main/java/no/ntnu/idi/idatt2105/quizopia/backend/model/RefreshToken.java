package no.ntnu.idi.idatt2105.quizopia.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

  private String refreshToken;
  private Boolean revoked;
  private Long user_id;

  public Boolean isRevoked() {
    return revoked;
  }
}
