package com.example.memoapplication.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserUpdateDto {
    @NotBlank(message = "유저의 수정할 Name이 누락되었습니다.")
    private String name;

    @NotBlank(message = "유저의 수정할 Email이 누락되었습니다.")
    private String email;
}
