package com.ovg.spatula.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CookieManager {

  public void addCookie(HttpServletResponse httpServletResponse, String name, String value,
      int expire) {
    Cookie cookie = new Cookie(name, value);
    cookie.setPath("/");
    cookie.setHttpOnly(true);
    cookie.setMaxAge(expire);
    httpServletResponse.addCookie(cookie);
  }

  public Map<String, String> getCookies(HttpServletRequest httpServletRequest,
      List<String> cookieList) {
    Map<String, String> result = new HashMap<>();
    Cookie[] cookies = httpServletRequest.getCookies();

    if (cookies == null) {
      return result;
    }

    for (Cookie cookie : cookies) {
      if (cookieList.contains(cookie.getName())) {
        result.put(cookie.getName(), cookie.getValue());
      }
    }
    return result;
  }
}
