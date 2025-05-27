package com.example.tuan3.exception;

import com.example.tuan3.response.ApiResponse;
import com.example.tuan3.response.ResponseUltils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Object>> handleDuplicate(ResourceAlreadyExistsException e) {
    return ResponseUltils.error("error.user.existed","Người dùng đã tồn tại");
  }
  @ExceptionHandler(Forbidden.class)
  public ResponseEntity<ApiResponse<Object>> handleForbidden(Forbidden e) {
    return ResponseUltils.error("error.auth.forbidden","Cần quyền để dùng dịch vụ này");
  }

}
