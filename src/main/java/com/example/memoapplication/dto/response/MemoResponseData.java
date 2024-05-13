package com.example.memoapplication.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemoResponseData {
    private final String title;
    private final String content;
}
