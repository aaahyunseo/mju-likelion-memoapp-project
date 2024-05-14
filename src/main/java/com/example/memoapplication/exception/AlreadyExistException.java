package com.example.memoapplication.exception;

import com.example.memoapplication.errorcode.ErrorCode;

public class AlreadyExistException extends CustomException {
    public AlreadyExistException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }
}
