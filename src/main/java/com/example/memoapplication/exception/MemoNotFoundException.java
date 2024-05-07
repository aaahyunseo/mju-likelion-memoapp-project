package com.example.memoapplication.exception;

import com.example.memoapplication.errorcode.ErrorCode;

public class MemoNotFoundException extends CustomException {
    //메모 정보를 찾을 수 없을 경우 예외처리
    public MemoNotFoundException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }
}
