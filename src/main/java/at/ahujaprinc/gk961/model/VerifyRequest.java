package at.ahujaprinc.gk961.model;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * VerifyRequest
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class VerifyRequest {
  @NotBlank private String token;
}
