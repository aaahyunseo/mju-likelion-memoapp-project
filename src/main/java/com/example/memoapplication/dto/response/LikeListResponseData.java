package com.example.memoapplication.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class LikeListResponseData {
    private final int likeCount;    //좋아요 수
    private List<LikeResponseDto> likeList;    //출력할 좋아요 리스트
}
