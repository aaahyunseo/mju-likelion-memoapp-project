package com.example.memoapplication.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LikeResponseDto {
    private final String title;     //메모 title
    private final String name;      //유저 name
}
