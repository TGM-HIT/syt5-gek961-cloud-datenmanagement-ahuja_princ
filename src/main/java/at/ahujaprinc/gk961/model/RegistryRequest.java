package at.ahujaprinc.gk961.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RegistryRequest
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistryRequest {
  @NotBlank private String name;
  @NotBlank @Email(message = "Invalid email address") private String username;
  @NotBlank private String[] roles;
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z0-9!@#$%"
                    + "^&*(),.?\":{}|<>]{8,}$",
           message = "Password must be at least 8 characters long, contain "
                     + "at least one number, and one special character.")
  @NotBlank
  private String password;
  @NotBlank private String token;
}
