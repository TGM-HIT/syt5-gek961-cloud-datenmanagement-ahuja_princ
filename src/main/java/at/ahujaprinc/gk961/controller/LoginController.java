package at.ahujaprinc.gk961.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.ahujaprinc.gk961.JWT;
import at.ahujaprinc.gk961.model.LoginRequest;
import at.ahujaprinc.gk961.model.LoginResponse;
import at.ahujaprinc.gk961.model.User;
import at.ahujaprinc.gk961.model.UserRepository;

/**
 * RegistrationController
 */
@RestController
@RequestMapping("/auth/signin")
public class LoginController {
  JWT jwtService = new JWT();
  @Autowired
  private UserRepository userRepository;

  @PostMapping
  public LoginResponse signin(@RequestBody LoginRequest request) {
    Optional<User> u = userRepository.findByUsername(request.getUsername());
    if(u.isEmpty()) return null;
    if(u.get().getPassword() == request.getPassword().hashCode()) {
      return new LoginResponse(jwtService.generateToken(u.get()));
    }
    return null;
  }
}
