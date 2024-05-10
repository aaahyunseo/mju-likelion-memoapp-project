package com.example.memoapplication.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class OrganizationCreateDto {
    @NotBlank(message = "Name이 누락되었습니다.")
    private String name;
}
