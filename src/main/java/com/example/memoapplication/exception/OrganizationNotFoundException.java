package com.example.memoapplication.exception;

import com.example.memoapplication.errorcode.ErrorCode;

public class OrganizationNotFoundException extends CustomException {
    //조직을 찾을 수 없을 때 예외처리
    public OrganizationNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
