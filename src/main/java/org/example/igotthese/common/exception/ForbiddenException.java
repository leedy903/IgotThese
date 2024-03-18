package org.example.igotthese.common.exception;

import org.example.igotthese.common.exhandler.ErrorCode;

public class ForbiddenException extends ApiException {
    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
