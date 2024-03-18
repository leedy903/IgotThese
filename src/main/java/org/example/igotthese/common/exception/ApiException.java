package org.example.igotthese.common.exception;

import lombok.Getter;
import org.example.igotthese.common.exhandler.ErrorCode;

@Getter
public abstract class ApiException extends RuntimeException{
    private ErrorCode errorCode;

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
