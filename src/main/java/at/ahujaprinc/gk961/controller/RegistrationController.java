package at.ahujaprinc.gk961.controller;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.ahujaprinc.gk961.JWT;
import at.ahujaprinc.gk961.model.RegistryRequest;
import at.ahujaprinc.gk961.model.RegistryResponse;
import at.ahujaprinc.gk961.model.User;
import at.ahujaprinc.gk961.model.UserRepository;
import io.jsonwebtoken.Claims;

/**
 * RegistrationController
 */
@RestController
@RequestMapping("/auth/admin/register")
public class RegistrationController {
  JWT jwtService = new JWT();
  @Autowired
  private UserRepository userRepository;

  @PostMapping
  public ResponseEntity<?> register(@RequestBody RegistryRequest request) {
    if(jwtService.isTokenExpired(request.getToken())) return ResponseEntity.status(403).body("Forbidden: You don't have permission to access this resource.");
    String[] roles = jwtService.extractClaim(request.getToken(), claims -> claims.get("roles", String[].class));
    for(String e : roles) {
      if(e == "ADMIN") {
        userRepository.saveAndFlush(new User(request.getName(), request.getUsername(), request.getPassword(), request.getRoles()));
        return null;
      }
    }
    return ResponseEntity.status(403).body("Forbidden: You don't have permission to access this resource.");
  }
}
