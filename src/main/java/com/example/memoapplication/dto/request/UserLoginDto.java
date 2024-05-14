package com.example.memoapplication.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserLoginDto {
    @NotBlank(message = "Email을 입력하세요.")
    @Email(message = "Email 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "Password를 입력하세요.")
    private String password;
}
