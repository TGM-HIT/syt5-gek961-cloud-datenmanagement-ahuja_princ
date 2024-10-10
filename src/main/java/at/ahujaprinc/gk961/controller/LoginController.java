package at.ahujaprinc.gk961.controller;

import at.ahujaprinc.gk961.JWT;
import at.ahujaprinc.gk961.Utilities;
import at.ahujaprinc.gk961.model.LoginRequest;
import at.ahujaprinc.gk961.model.User;
import at.ahujaprinc.gk961.model.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RegistrationController
 */
@RestController
@RequestMapping("/auth/signin")
public class LoginController {
  JWT jwtService;
  @Autowired private UserRepository userRepository;

  @PostMapping
  public ResponseEntity<?> signin(@RequestBody LoginRequest request) {
    Optional<User> u = userRepository.findByUsername(request.getUsername());
    // If user not found exit
    if (u.isEmpty()) {
      return ResponseEntity.status(404).body("Invalid credentials");
    }
    // hash password and compare with DB, return token if its fit
    String hashedPassword = Utilities.hashString(request.getPassword());
    if (u.get().getPassword().equals(hashedPassword)) {
      return ResponseEntity.status(200).body(JWT.generateToken(u.get()));
    }

    return ResponseEntity.status(404).body("Invalid credentials");
  }
}
