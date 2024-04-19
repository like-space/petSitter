package com.group.petSitter.global.auth.controller;

import com.group.petSitter.global.auth.exception.*;
import com.group.petSitter.global.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponse> authExHandle(AuthException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("AUTH_ERROR_CODE") // 예외 코드
                .status(HttpStatus.BAD_REQUEST.value()) // HTTP 상태 코드
                .message(ex.getMessage()) // 예외 메시지
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(OAuthUnlinkFailureException.class)
    public ResponseEntity<ErrorResponse> oAuthUnlinkFailureExHandle(OAuthUnlinkFailureException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("OAUTH_UNLINK_FAILURE_ERROR_CODE") // 예외 코드
                .status(HttpStatus.UNAUTHORIZED.value()) // HTTP 상태 코드
                .message(ex.getMessage()) // 예외 메시지
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(UnAuthenticationException.class)
    public ResponseEntity<ErrorResponse> unAuthenticationExHandle(UnAuthenticationException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("UNAUTHENTICATION_ERROR_CODE") // 예외 코드
                .status(HttpStatus.UNAUTHORIZED.value()) // HTTP 상태 코드
                .message(ex.getMessage()) // 예외 메시지
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(InvalidJwtException.class)
    public ResponseEntity<ErrorResponse> invalidJwtExHandle(InvalidJwtException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("INVALID_JWT_ERROR_CODE") // 예외 코드
                .status(HttpStatus.UNAUTHORIZED.value()) // HTTP 상태 코드
                .message(ex.getMessage()) // 예외 메시지
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<ErrorResponse> duplicateUsernameExHandle(DuplicateUsernameException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("DUPLICATE_USERNAME_ERROR_CODE") // 예외 코드
                .status(HttpStatus.CONFLICT.value()) // HTTP 상태 코드
                .message(ex.getMessage()) // 예외 메시지
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> bindExHandle(BindException ex) {
        //파라미터 validation 걸렸을 경우
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("NULL_ERROR_CODE") // 예외 코드
                .status(HttpStatus.CONFLICT.value()) // HTTP 상태 코드
                .message(ex.getMessage()) // 예외 메시지
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}
