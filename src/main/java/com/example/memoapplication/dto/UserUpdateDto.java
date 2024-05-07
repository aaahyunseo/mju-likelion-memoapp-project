package com.example.memoapplication.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserUpdateDto {
    @NotBlank(message = "userName이 누락되었습니다.")
    private String name;

    @NotBlank(message = "userEmail이 누락되었습니다.")
    private String email;
}
