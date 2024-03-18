package org.example.igotthese.common.exhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private int value;
    private HttpStatus httpStatus;
    private String message;

    public static ErrorResponse of(int value, HttpStatus status, String message) {
        return new ErrorResponse(value, status, message);
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        int value = errorCode.getValue();
        HttpStatus status = errorCode.getStatus();
        String message = errorCode.getMessage();
        return new ErrorResponse(value, status, message);
    }
}
