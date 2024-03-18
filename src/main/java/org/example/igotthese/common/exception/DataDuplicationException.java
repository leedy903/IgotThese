package org.example.igotthese.common.exception;

import org.example.igotthese.common.exhandler.ErrorCode;

public class DataDuplicationException extends ApiException {
    public DataDuplicationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
