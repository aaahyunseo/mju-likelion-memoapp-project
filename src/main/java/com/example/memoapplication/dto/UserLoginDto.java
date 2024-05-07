package com.example.memoapplication.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserLoginDto {
    @NotBlank(message = "emil을 입력하세요.")
    private String email;

    @NotBlank(message = "password를 입력하세요.")
    private String password;
}
