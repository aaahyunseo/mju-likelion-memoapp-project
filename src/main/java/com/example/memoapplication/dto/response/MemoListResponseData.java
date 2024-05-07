package com.example.memoapplication.dto.response;

import com.example.memoapplication.model.Memo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class MemoListResponseData {
    private List<Memo> memoList;
}
