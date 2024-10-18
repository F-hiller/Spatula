package com.ovg.spatula.exception.exceptions;

import java.util.NoSuchElementException;

public class NoSuchUserException extends NoSuchElementException {

  public NoSuchUserException(String message) {
    super(message);
  }

  public NoSuchUserException() {
    super("조건에 맞는 사용자를 찾을 수 없음.");
  }
}
