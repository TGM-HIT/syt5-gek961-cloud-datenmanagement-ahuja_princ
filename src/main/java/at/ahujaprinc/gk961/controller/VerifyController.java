package at.ahujaprinc.gk961.controller;

import at.ahujaprinc.gk961.JWT;
import at.ahujaprinc.gk961.model.User;
import at.ahujaprinc.gk961.model.UserRepository;
import at.ahujaprinc.gk961.model.VerifyRequest;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RegistrationController
 */
@RestController
@RequestMapping("/auth/verify")
public class VerifyController {
  @Autowired private UserRepository userRepository;

  @GetMapping
  public ResponseEntity<?> verify(@RequestBody VerifyRequest request) {
    String token = request.getToken();
    if (JWT.validateToken(token)) {
      String name = JWT.extractClaim(token, "name", String.class);
      String username = JWT.extractSubject(token);
      Optional<User> u = userRepository.findByUsername(username);
      if (!u.isEmpty()) {
        String[] roles = u.get().getRoles();
        StringBuilder appendedRoles = new StringBuilder();
        for (String role : roles) {
          appendedRoles.append(role).append(" ");
        }
        return ResponseEntity.status(HttpStatus.OK.value()).body(
            "Hello " + name + "! Your roles are " + appendedRoles);
      }
    }

    return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(
        "Forbidden: You don't have permission to access this resource.");
  }
}
