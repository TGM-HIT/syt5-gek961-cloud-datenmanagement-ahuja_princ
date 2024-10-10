package at.ahujaprinc.gk961.controller;

import at.ahujaprinc.gk961.Utilities;
import at.ahujaprinc.gk961.model.User;
import at.ahujaprinc.gk961.model.UserInit;
import at.ahujaprinc.gk961.model.UserRepository;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * InitializeController
 */
@RestController
@RequestMapping("/init")
public class InitializeController {
  @Autowired private UserRepository userRepository;
  @PutMapping
  public boolean init() {
    ObjectMapper objectMapper = new ObjectMapper();

    try (
        InputStream inputStream =
            InitializeController.class.getClassLoader().getResourceAsStream("user.json")) {
      UserInit[] users =
          objectMapper.readValue(inputStream, UserInit[].class);
      for (UserInit user : users) {
        String name = user.getName();
        String username = user.getUsername();
        String[] userRoles = user.getRoles();
        String password = user.getPassword();
        // hash password before inserting
        password = Utilities.hashString(password);
        User newUser = new User(name, username, password, userRoles);
        userRepository.save(newUser);
      }
      userRepository.flush();
      return true;
    } catch (StreamReadException e) {
      System.err.println("Can't parse JSON.");
    } catch (DatabindException e) {
      System.err.println("Can't bind JSON to provided data class.");
    } catch (IOException e) {
      System.err.println("Can't read JSON file.");
    }
    return false;
  }
}
