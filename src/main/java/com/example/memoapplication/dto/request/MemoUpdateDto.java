package com.example.memoapplication.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MemoUpdateDto {
    @NotNull(message = "메모 title이 null입니다")
    private String title;

    @NotNull(message = "메모 content가 null입니다")
    private String content;
}
