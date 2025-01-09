package at.ahujaprinc.gk961.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * RegistryRequest
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ActivationRequest {
  @NotBlank @Email(message = "Invalid email address") private String username;
  @NotBlank private String code;
}
