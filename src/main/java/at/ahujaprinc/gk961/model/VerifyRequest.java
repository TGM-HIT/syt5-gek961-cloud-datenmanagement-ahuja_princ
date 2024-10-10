package at.ahujaprinc.gk961.model;

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
  private String token;
}
