package com.example.memoapplication.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class MemoListResponseData {
    private List<MemoResponseData> memoList;
}
