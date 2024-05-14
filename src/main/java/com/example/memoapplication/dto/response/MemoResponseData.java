package com.example.memoapplication.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemoResponseData {
    private final String title;     //메모 title
    private final String content;   //메모 content
}
