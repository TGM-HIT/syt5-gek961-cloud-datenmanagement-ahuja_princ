package at.ahujaprinc.gk961.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * LoginRequest
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoginRequest {
  @NotBlank @Email(message = "Invalid email address") private String username;

  @NotBlank
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z0-9!@#$%" +
                    "^&*(),.?\":{}|<>]{8,}$",
           message = "Password must be at least 8 characters long, contain " +
                     "at least one number, and one special character.")
  private String password;
}
