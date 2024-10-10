package at.ahujaprinc.gk961.controller;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.ahujaprinc.gk961.model.LoginRequest;
import at.ahujaprinc.gk961.model.LoginResponse;

/**
 * RegistrationController
 */
@RestController
@RequestMapping("/auth/signin")
public class LoginController {
  @PostMapping
  public LoginResponse signin(@RequestBody LoginRequest request) {
    return null;
  }
}
