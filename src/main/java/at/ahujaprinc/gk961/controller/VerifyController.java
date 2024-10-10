package at.ahujaprinc.gk961.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.ahujaprinc.gk961.model.VerifyRequest;
import at.ahujaprinc.gk961.model.VerifyResponse;

/**
 * RegistrationController
 */
@RestController
@RequestMapping("/auth/verify")
public class VerifyController {
  JWT jwtService = new JWT();

  @GetMapping
  public VerifyResponse verify(@RequestBody VerifyRequest request) {
    return null;
  }
}
