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
  private String name;
  private String username;
  private String[] roles;
  private int password;
  private String token;
}
