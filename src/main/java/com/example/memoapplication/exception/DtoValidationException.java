package com.example.memoapplication.exception;

import com.example.memoapplication.errorcode.ErrorCode;

public class DtoValidationException extends CustomException {
    public DtoValidationException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }
}