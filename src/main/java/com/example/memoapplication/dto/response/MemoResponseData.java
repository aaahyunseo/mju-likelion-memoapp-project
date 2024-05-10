package com.example.memoapplication.dto.response;

import com.example.memoapplication.model.Memo;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemoResponseData {
    private final Memo memo;
}
