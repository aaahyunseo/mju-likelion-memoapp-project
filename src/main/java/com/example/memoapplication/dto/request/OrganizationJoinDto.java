package com.example.memoapplication.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class OrganizationJoinDto {
    @NotBlank(message = "role이 누락되었습니다.")
    private String role;
}
