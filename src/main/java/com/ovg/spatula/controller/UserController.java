package com.ovg.spatula.controller;

import com.ovg.spatula.dto.UserResponse;
import com.ovg.spatula.service.UserService;
import com.ovg.spatula.util.CookieManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

  private final UserService userService;
  private final CookieManager cookieManager;

  public UserController(UserService userService, CookieManager cookieManager) {
    this.userService = userService;
    this.cookieManager = cookieManager;
  }

  @GetMapping("/code")
  public ResponseEntity<?> assignUserId(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse) {
    Map<String, String> map = cookieManager.getCookies(httpServletRequest, List.of("code"));
    String code = map.get("code");
    if (code != null) {
      return ResponseEntity.ok("User code already exists.");
    }
    UserResponse userResponse = userService.addUser();
    cookieManager.addCookie(httpServletResponse, "code", userResponse.getCode(),
        60 * 60 * 24 * 365);

    return ResponseEntity.ok(userResponse);
  }

  @GetMapping("/info")
  public ResponseEntity<UserResponse> getUserInfo(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse) {
    Map<String, String> map = cookieManager.getCookies(httpServletRequest, List.of("code"));
    String code = map.get("code");
    if (code == null) {
      throw new NoSuchElementException("User code not exist.");
    }
    UserResponse userResponse = userService.getUserInfo(code);

    return ResponseEntity.ok(userResponse);
  }
}