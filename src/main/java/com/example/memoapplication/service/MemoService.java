package com.example.memoapplication.service;

import com.example.memoapplication.dto.MemoCreateDto;
import com.example.memoapplication.dto.MemoUpdateDto;
import com.example.memoapplication.dto.response.LikeListResponseData;
import com.example.memoapplication.dto.response.MemoListResponseData;
import com.example.memoapplication.dto.response.MemoResponseData;
import com.example.memoapplication.model.Memo;
import com.example.memoapplication.model.MemoLike;
import com.example.memoapplication.repository.MemoRepository;
import com.example.memoapplication.repository.UserJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MemoService {
    private final MemoRepository memoRepository;
    private final UserJpaRepository userJpaRepository;

    //메모 생성
    public void createMemo(MemoCreateDto memoCreateDto, UUID userId) {
        UUID uuid = UUID.randomUUID();
        Memo newMemo = Memo.builder()
                .id(uuid)
                .user(userJpaRepository.findUserById(userId))
                .title(memoCreateDto.getTitle())
                .content(memoCreateDto.getContent())
                .build();

        memoRepository.createMemo(newMemo);
    }

    //메모 전체 조회
    public MemoListResponseData getAllMemo(UUID userId) {
        List<Memo> memoList = this.memoRepository.getAllMemo(userId);
        MemoListResponseData memoListResponseData = MemoListResponseData.builder().memoList(memoList).build();
        return memoListResponseData;
    }

    //id로 특정 메모 조회
    public MemoResponseData getMemoById(UUID userId, UUID id) {
        MemoResponseData memoResponseData = MemoResponseData.builder().memo(memoRepository.getMemoById(userId, id)).build();
        return memoResponseData;
    }

    //id로 특정 메모 삭제
    public void deleteMemoById(UUID userId, UUID id) {
        memoRepository.deleteMemoById(userId, id);
    }

    //id로 특정 메모 수정
    public void updateMemoById(MemoUpdateDto memoUpdateDto, UUID userId, UUID id) {
        Memo memoToUpdate = memoRepository.getMemoById(userId, id);
        memoToUpdate.setTitle(memoUpdateDto.getTitle());
        memoToUpdate.setContent(memoUpdateDto.getContent());

        memoRepository.updateMemoById(memoToUpdate, userId);
    }

    //메모 like 기능 구현
    public void likeMemo(UUID userId, UUID id) {
        memoRepository.likeMemo(userId, id);
    }

    //likeList 조회
    public LikeListResponseData getLikeList(UUID id) {
        List<MemoLike> likeList = this.memoRepository.getLikeList(id);
        LikeListResponseData likeListResponseData = LikeListResponseData.builder()
                .likeList(likeList)
                .likeCount(likeList.size())
                .build();
        return likeListResponseData;
    }
}
