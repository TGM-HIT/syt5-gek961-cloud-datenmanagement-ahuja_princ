package at.ahujaprinc.gk961.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RegistryRequest
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivationRequest {
  @NotBlank @Email(message = "Invalid email address") private String username;
  @NotBlank private String code;
}
