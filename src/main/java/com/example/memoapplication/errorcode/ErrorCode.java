package com.example.memoapplication.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND("4040", "유저를 찾을 수 없습니다."),
    MEMO_NOT_FOUND("4041", "메모를 찾을 수 없습니다."),
    ORGANIZATION_NOT_FOUND("4042", "조직을 찾을 수 없습니다."),
    TOKEN_NOT_FOUND("4043", "토큰을 찾을 수 없습니다."),
    ALREADY_EXIST("4030", "이미 존재하는 회원입니다."),
    LOGIN_FALSE("4031", "로그인에 실패하였습니다."),
    TOKEN_INVALID("4033", "유효하지 않는 토큰입니다."),
    NOT_NULL("9001", "필수값이 누락되었습니다."),
    NOT_BLANK("9002", "필수값이 빈 값이거나 공백으로 되어있습니다.");

    private final String code;      //에러 코드
    private final String message;   //에러 메세지

    //Dto의 어노테이션을 통해 발생한 에러코드를 반환
    public static ErrorCode resolveValidationErrorCode(String code) {
        return switch (code) {
            case "NotNull" -> NOT_NULL;
            case "NotBlank" -> NOT_BLANK;
            default -> throw new IllegalArgumentException("Unexpected value: " + code);
        };
    }
}
