package at.ahujaprinc.gk961.model;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * VerifyRequest
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyRequest {
  @NotBlank private String token;
}
