package com.example.memoapplication.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserCreateDto {
    @NotBlank(message = "userName이 누락되었습니다.")
    private String name;

    @NotBlank(message = "userEmail이 누락되었습니다.")
    private String email;

    @NotBlank(message = "userPassword가 누락되었습니다.")
    private String password;
}
