package com.example.memoapplication.exception;

import com.example.memoapplication.errorcode.ErrorCode;

public class ForbiddenException extends CustomException {
    public ForbiddenException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }
}
