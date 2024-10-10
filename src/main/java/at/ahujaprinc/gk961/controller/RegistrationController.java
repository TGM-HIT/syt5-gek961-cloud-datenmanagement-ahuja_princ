package at.ahujaprinc.gk961.controller;

import at.ahujaprinc.gk961.JWT;
import at.ahujaprinc.gk961.Utilities;
import at.ahujaprinc.gk961.model.RegistryRequest;
import at.ahujaprinc.gk961.model.User;
import at.ahujaprinc.gk961.model.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RegistrationController
 */
@RestController
@RequestMapping("/auth/admin/register")
public class RegistrationController {
  @Autowired private UserRepository userRepository;

  @PutMapping
  public ResponseEntity<?> register(@RequestBody RegistryRequest request) {
    String token = request.getToken();
    if (JWT.validateToken(token)) {
      String username = JWT.extractSubject(token);
      Optional<User> u = userRepository.findByUsername(username);
      if (!u.isEmpty()) {
        String[] roles = u.get().getRoles();
        // Only admins are permitted to perform this action, thus check if this
        // user is a admin if so, perform the action
        for (String role : roles) {
          if (role.equals("ADMIN")) {
            String name = request.getName();
            String newUsername = request.getUsername();
            String[] userRoles = request.getRoles();
            String password = request.getPassword();
            // hash password before inserting
            password = Utilities.hashString(password);
            User newUser = new User(name, newUsername, password, userRoles);
            userRepository.saveAndFlush(newUser);
            return ResponseEntity.status(200).body("Account created for " +
                                                   newUsername);
          }
        }
      }
    }

    return ResponseEntity.status(403).body(
        "Forbidden: You don't have permission to access this resource.");
  }
}
