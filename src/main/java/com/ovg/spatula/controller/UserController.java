package com.ovg.spatula.controller;

import com.ovg.spatula.entity.User;
import com.ovg.spatula.service.UserService;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<User> addUser(@RequestParam String deviceId, @RequestParam String name) {
    User user = userService.addUser(deviceId, name);
    return ResponseEntity.ok(user);
  }

  @GetMapping("/{deviceId}")
  public ResponseEntity<User> getUserByDeviceId(@PathVariable String deviceId) {
    Optional<User> userOptional = userService.findUserByDeviceId(deviceId);
    return userOptional.map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}