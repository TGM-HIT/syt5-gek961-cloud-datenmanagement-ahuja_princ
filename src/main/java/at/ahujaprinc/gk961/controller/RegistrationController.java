package at.ahujaprinc.gk961.controller;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.ahujaprinc.gk961.model.RegistryRequest;
import at.ahujaprinc.gk961.model.RegistryResponse;

/**
 * RegistrationController
 */
@RestController
@RequestMapping("/auth/admin/register")
public class RegistrationController {
  @PostMapping
  public RegistryResponse register(@RequestBody RegistryRequest request) {
    return null;
  }
}
