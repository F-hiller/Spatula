package com.ovg.spatula.exception.exceptions;

import java.util.NoSuchElementException;

public class NoSuchCodeException extends NoSuchElementException {

  public NoSuchCodeException() {
    super("사용자 인증을 위한 쿠키를 찾을 수 없음.");
  }
}
