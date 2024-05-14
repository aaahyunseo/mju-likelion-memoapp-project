package com.example.memoapplication.exception;

import com.example.memoapplication.errorcode.ErrorCode;

public class InvalidTokenException extends CustomException {
    public InvalidTokenException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }
}
