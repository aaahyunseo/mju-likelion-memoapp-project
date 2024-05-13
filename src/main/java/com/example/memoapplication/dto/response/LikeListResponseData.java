package com.example.memoapplication.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LikeListResponseData {
    private final int likeCount;    //좋아요 수
    private final String title;     //메모 title
    //private List<MemoLike> likeList;
}
