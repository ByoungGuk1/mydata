package com.app.mydata.global.exception;

import com.app.mydata.domain.member.exception.MemberException;
import com.app.mydata.domain.member.exception.MemberNotFoundException;
import com.app.mydata.domain.mydata.exception.MydataRiaAccountException;
import com.app.mydata.domain.mydata.exception.MydataRiaAccountNotFoundException;
import com.app.mydata.domain.mydata.exception.MydataTradeException;
import com.app.mydata.domain.mydata.exception.MydataTradeNotFoundException;
import com.app.mydata.global.response.ApiResponseDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(MemberException.class)
  public ResponseEntity<ApiResponseDTO<String>> handleException(MemberException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDTO.of(e.getMessage()));
  }
  @ExceptionHandler(MemberNotFoundException.class)
  public ResponseEntity<ApiResponseDTO<Void>>handleMemberNotFound(MemberNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseDTO.of(e.getMessage()));
  }

  @ExceptionHandler(MydataTradeException.class)
  public ResponseEntity<ApiResponseDTO<Void>> handleMydataTradeException(MydataTradeException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDTO.of(e.getMessage()));
  }
  @ExceptionHandler(MydataTradeNotFoundException.class)
  public ResponseEntity<ApiResponseDTO<Void>> handleMydataTradeNotFound(MydataTradeNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseDTO.of(e.getMessage()));
  }

  @ExceptionHandler(MydataRiaAccountException.class)
  public ResponseEntity<ApiResponseDTO<Void>> handleMydataRiaAccountException(MydataRiaAccountException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDTO.of(e.getMessage()));
  }
  @ExceptionHandler(MydataRiaAccountNotFoundException.class)
  public ResponseEntity<ApiResponseDTO<Void>> handleMydataRiaAccountNotFound(MydataRiaAccountNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseDTO.of(e.getMessage()));
  }

  @ExceptionHandler(BindException.class)
  public ResponseEntity<ApiResponseDTO<Void>> handleBindException(BindException e) {
    String message = e.getBindingResult().getFieldErrors().stream()
            .findFirst()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .orElse("요청값이 올바르지 않습니다.");
    return ResponseEntity.badRequest().body(ApiResponseDTO.of(message));
  }
}
