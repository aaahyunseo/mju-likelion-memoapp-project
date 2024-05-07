package com.example.memoapplication.exception;

import com.example.memoapplication.errorcode.ErrorCode;

public class LoginFalseException extends CustomException {
    //로그인 실패 시 예외처리
    public LoginFalseException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }
}
