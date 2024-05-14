package com.example.memoapplication.service;

import com.example.memoapplication.dto.request.MemoCreateDto;
import com.example.memoapplication.dto.request.MemoUpdateDto;
import com.example.memoapplication.dto.response.LikeListResponseData;
import com.example.memoapplication.dto.response.LikeResponseDto;
import com.example.memoapplication.dto.response.MemoListResponseData;
import com.example.memoapplication.dto.response.MemoResponseData;
import com.example.memoapplication.errorcode.ErrorCode;
import com.example.memoapplication.exception.AlreadyExistException;
import com.example.memoapplication.exception.NotFoundException;
import com.example.memoapplication.model.Memo;
import com.example.memoapplication.model.MemoLike;
import com.example.memoapplication.model.User;
import com.example.memoapplication.repository.LikeJpaRepository;
import com.example.memoapplication.repository.MemoJpaRepository;
import com.example.memoapplication.repository.UserJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class MemoService {
    private final MemoJpaRepository memoJpaRepository;
    private final LikeJpaRepository likeJpaRepository;
    private final UserJpaRepository userJpaRepository;

    //메모 생성
    public void createMemo(MemoCreateDto memoCreateDto, User user) {
        Memo newMemo = Memo.builder()
                .user(userJpaRepository.findUserById(user.getId()))
                .title(memoCreateDto.getTitle())
                .content(memoCreateDto.getContent())
                .build();
        memoJpaRepository.save(newMemo);
    }

    //메모 전체 조회
    public MemoListResponseData getAllMemo(User user) {
        userExistsInMemo(user);
        List<Memo> memoList = memoJpaRepository.findAllByUser(user);
        List<MemoResponseData> list = new ArrayList<>();
        for (Memo memo : memoList) {
            MemoResponseData memoResponseData = MemoResponseData.builder()
                    .title(memo.getTitle())
                    .content(memo.getContent())
                    .build();
            list.add(memoResponseData);
        }
        MemoListResponseData memoListResponseData = MemoListResponseData.builder().memoList(list).build();
        return memoListResponseData;
    }

    //id로 특정 메모 조회
    public MemoResponseData getMemoById(User user, UUID id) {
        userExistsInMemo(user);
        memoExists(id);
        Memo memo = memoJpaRepository.findMemoById(id);
        MemoResponseData memoResponseData = MemoResponseData.builder()
                .title(memo.getTitle())
                .content(memo.getContent())
                .build();
        return memoResponseData;
    }

    //id로 특정 메모 삭제
    public void deleteMemoById(User user, UUID id) {
        userExistsInMemo(user);
        memoExists(id);
        memoJpaRepository.deleteById(id);
    }

    //id로 특정 메모 수정
    public void updateMemoById(MemoUpdateDto memoUpdateDto, User user, UUID id) {
        userExistsInMemo(user);
        memoExists(id);
        Memo memoToUpdate = memoJpaRepository.findMemoByUser(user);
        memoToUpdate.setTitle(memoUpdateDto.getTitle());
        memoToUpdate.setContent(memoUpdateDto.getContent());

        memoJpaRepository.save(memoToUpdate);
    }

    //메모 like 기능 구현
    public void likeMemo(User user, UUID id) {
        if (!likeJpaRepository.existsByUser(user)) {    //userId 중복 검사
            MemoLike like = MemoLike.builder()
                    .memo(memoJpaRepository.findMemoById(id))
                    .user(userJpaRepository.findUserById(user.getId()))
                    .build();
            likeJpaRepository.save(like);
            return;
        }
        throw new AlreadyExistException(ErrorCode.ALREADY_EXIST, "이미 좋아요를 눌렀습니다.");
    }

    //likeList 조회
    public LikeListResponseData getLikeList(UUID id) {
        memoExists(id);
        Memo memo = memoJpaRepository.findMemoById(id);
        List<MemoLike> likeList = likeJpaRepository.findAllByMemo(memo);
        List<LikeResponseDto> list = new ArrayList<>();

        for (MemoLike memoLike : likeList) {
            LikeResponseDto likeResponseDto = LikeResponseDto.builder()
                    .name(memoLike.getUser().getName())
                    .title(memoLike.getMemo().getTitle())
                    .build();
            list.add(likeResponseDto);
        }

        LikeListResponseData likeListResponseData = LikeListResponseData.builder()
                .likeCount(likeList.size())
                .likeList(list)
                .build();
        return likeListResponseData;
    }

    //메모 안 user 존재 여부
    public void userExistsInMemo(User user) {
        if (!memoJpaRepository.existsByUser(user)) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
        }
    }

    //메모 존재 여부
    public void memoExists(UUID id) {
        if (!memoJpaRepository.existsById(id)) {
            throw new NotFoundException(ErrorCode.MEMO_NOT_FOUND);
        }
    }
}
