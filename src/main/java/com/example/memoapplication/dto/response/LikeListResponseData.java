package com.example.memoapplication.dto.response;

import com.example.memoapplication.model.MemoLike;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class LikeListResponseData {
    private final int likeCount;    //좋아요 수
    private List<MemoLike> likeList;
}
