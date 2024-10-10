package at.ahujaprinc.gk961.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;
  private String username;
  private int password;
  private String[] roles;

  public User(String name, String username, int password, String[] roles) {
    this.name = name;
    this.username = username;
    this.password = password;
    this.roles = roles;
    }
}
