package com.ovg.spatula.exception;

import com.ovg.spatula.dto.response.ErrorResponse;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleIllegalArgumentException() {
    ErrorResponse errorResponse = new ErrorResponse("IllegalArgument: ",
        HttpStatus.BAD_REQUEST.value());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<ErrorResponse> handleNoSuchElementException() {
    ErrorResponse errorResponse = new ErrorResponse("NoSuchElement: ",
        HttpStatus.BAD_REQUEST.value());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NullPointerException.class)
  public ResponseEntity<ErrorResponse> handleNullPointerException() {
    ErrorResponse errorResponse = new ErrorResponse("NullPointer: ",
        HttpStatus.INTERNAL_SERVER_ERROR.value());
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGlobalException() {
    ErrorResponse errorResponse = new ErrorResponse("서버 내부 오류.",
        HttpStatus.INTERNAL_SERVER_ERROR.value());
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
