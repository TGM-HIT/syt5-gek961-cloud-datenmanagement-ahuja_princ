package at.ahujaprinc.gk961.model;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UserInit {
  private String name;
  private String username;
  private String password;
  private String[] roles;
}
