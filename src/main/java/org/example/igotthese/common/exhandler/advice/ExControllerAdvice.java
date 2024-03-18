package org.example.igotthese.common.exhandler.advice;

import lombok.extern.slf4j.Slf4j;
import org.example.igotthese.common.exception.ApiException;
import org.example.igotthese.common.exhandler.ErrorCode;
import org.example.igotthese.common.exhandler.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validationExHandler(MethodArgumentNotValidException e) {
        ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append(errorCode.getMessage()).append("\n");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMessage.append("[");
            errorMessage.append(fieldError.getField());
            errorMessage.append("](은)는 ");
            errorMessage.append(fieldError.getDefaultMessage());
            errorMessage.append(". ");
        }
        errorMessage.deleteCharAt(errorMessage.length() - 1);
        ErrorResponse response = ErrorResponse.of(errorCode.getValue(), errorCode.getStatus(), errorMessage.toString());
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(response);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> apiExHandler(ApiException e) {
        log.error("[ExceptionHandler]", e);
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.of(errorCode));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exHandler(Exception e) {
        log.error("[ExceptionHandler]", e);
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of(errorCode));
    }
}
