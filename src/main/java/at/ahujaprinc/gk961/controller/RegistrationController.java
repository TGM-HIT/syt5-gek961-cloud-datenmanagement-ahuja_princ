
package at.ahujaprinc.gk961.controller;

import at.ahujaprinc.gk961.JWT;
import at.ahujaprinc.gk961.Utilities;
import at.ahujaprinc.gk961.model.ActivationRequest;
import at.ahujaprinc.gk961.model.RegistryRequest;
import at.ahujaprinc.gk961.model.User;
import at.ahujaprinc.gk961.model.UserRepository;
import at.ahujaprinc.gk961.service.EmailService;
import at.ahujaprinc.gk961.service.RateLimiterService;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/admin/register")
public class RegistrationController {
  @Autowired private UserRepository userRepository;
  @Autowired private EmailService emailService;
  @Autowired private RateLimiterService rateLimiterService;

  private static final long ACTIVATION_CODE_EXPIRATION_MINUTES = 5;
  private static final String CODE_CHARSET =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; // Alphanumeric characters
  private static final int CODE_LENGTH = 6;

  private Map<String, ActivationDetails> activationStorage = new HashMap<>();

  @PutMapping
  public ResponseEntity<?> register(@RequestBody RegistryRequest request) {
    String token = request.getToken();

    if (!rateLimiterService.use(token))
      return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body("Too many requests!");

    if (JWT.validateToken(token)) {
      String adminUser = JWT.extractSubject(token);
      Optional<User> u = userRepository.findByUsername(adminUser);
      if (u.isPresent() && hasAdminRole(u.get())) {
        String username = request.getUsername();

        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
          emailService.sendEmail(
              username, "Security Alert: Registration Attempt",
              "A registration attempt was made for your account (" + username +
                  ("). If this was not you, please contact support "
                   + "immediately."));
        } else {
          // Generate a secure 6-character activation code
          String activationCode = generateActivationCode();
          Instant expiryTime = Instant.now().plusSeconds(
              ACTIVATION_CODE_EXPIRATION_MINUTES * 60);

          activationStorage.put(activationCode,
                                new ActivationDetails(request, expiryTime));

          emailService.sendEmail(
              username, "Activation Code",
              "Your activation code is: " + activationCode +
                  ". This code is valid for " +
                  ACTIVATION_CODE_EXPIRATION_MINUTES +
                  " minutes. Please use it to activate your account.");
        }
        return ResponseEntity.status(HttpStatus.OK.value()).body(
            "Please check your inbox for the activation code.");
      }
    }
    return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(
        "Forbidden: You don't have permission to access this resource.");
  }

  @PostMapping("/activate")
  public ResponseEntity<?> activateAccount(@RequestBody ActivationRequest req) {
    ActivationDetails details = activationStorage.get(req.getCode());
    if (details != null && !rateLimiterService.use(details.request.getToken()))
      return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body("Too many requests!");

    if (details != null) {
      if (Instant.now().isAfter(details.expiryTime)) {
        activationStorage.remove(req.getCode());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body("Activation code has expired.");
      }

      RegistryRequest request = details.request;
      if (request.getUsername().equals(req.getUsername())) {
        activationStorage.remove(req.getCode());

        String hashedPassword = Utilities.hashString(request.getPassword());
        User newUser = new User(request.getName(), request.getUsername(),
                                hashedPassword, request.getRoles());
        userRepository.saveAndFlush(newUser);

        emailService.sendEmail(
            request.getUsername(), "Welcome to Our Service!",
            "Your account (" + request.getUsername() +
                ") has been successfully activated. You can now log in.");
        return ResponseEntity.status(HttpStatus.OK.value()).body(
            "Account successfully activated!");
      }
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(
        "Invalid activation code or username.");
  }

  private boolean hasAdminRole(User user) {
    for (String role : user.getRoles()) {
      if ("ADMIN".equals(role)) {
        return true;
      }
    }
    return false;
  }

  // Helper method to generate a secure 6-character activation code
  private String generateActivationCode() {
    SecureRandom random = new SecureRandom();
    StringBuilder code = new StringBuilder(CODE_LENGTH);
    for (int i = 0; i < CODE_LENGTH; i++) {
      int index = random.nextInt(CODE_CHARSET.length());
      code.append(CODE_CHARSET.charAt(index));
    }
    return code.toString();
  }

  // Helper class to store activation details
  private static class ActivationDetails {
    RegistryRequest request;
    Instant expiryTime;

    ActivationDetails(RegistryRequest request, Instant expiryTime) {
      this.request = request;
      this.expiryTime = expiryTime;
    }
  }
}
