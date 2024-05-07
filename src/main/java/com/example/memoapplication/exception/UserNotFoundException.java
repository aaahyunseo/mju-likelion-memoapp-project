package com.example.memoapplication.exception;

import com.example.memoapplication.errorcode.ErrorCode;

public class UserNotFoundException extends CustomException {
    //유저 정보를 찾을 수 없을 경우 예외처리
    public UserNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
