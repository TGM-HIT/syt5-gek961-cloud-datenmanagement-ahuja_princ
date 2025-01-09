package at.ahujaprinc.gk961.controller;

import at.ahujaprinc.gk961.JWT;
import at.ahujaprinc.gk961.Utilities;
import at.ahujaprinc.gk961.model.LoginRequest;
import at.ahujaprinc.gk961.model.User;
import at.ahujaprinc.gk961.model.UserRepository;
import at.ahujaprinc.gk961.service.RateLimiterService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
  @Autowired private RateLimiterService rateLimiterService;

  @PostMapping
  public ResponseEntity<?> signin(@RequestBody LoginRequest request) {
    if (!rateLimiterService.use(request.getUsername()))
      return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body("Too many requests!");

    Optional<User> u = userRepository.findByUsername(request.getUsername());
    // If user not found exit
    if (!u.isEmpty()) {
      // hash password and compare with DB, return token if its fit
      if (Utilities.checkHash(request.getPassword(), u.get().getPassword())) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(JWT.generateToken(u.get()));
      }
    }

    return ResponseEntity.status(HttpStatus.OK.value()).body("Invalid credentials");
  }
}
