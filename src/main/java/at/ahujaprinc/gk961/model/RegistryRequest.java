package at.ahujaprinc.gk961.model;

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
  private String username;
  private String[] roles;
  private String password;
}
