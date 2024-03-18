package org.example.igotthese.common.exception;

import org.example.igotthese.common.exhandler.ErrorCode;

public class NoSuchDataException extends ApiException {
    public NoSuchDataException(ErrorCode errorCode) {
        super(errorCode);
    }
}
