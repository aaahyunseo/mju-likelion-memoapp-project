package com.example.memoapplication.service;

import com.example.memoapplication.dto.request.MemoCreateDto;
import com.example.memoapplication.dto.request.MemoUpdateDto;
import com.example.memoapplication.dto.response.LikeListResponseData;
import com.example.memoapplication.dto.response.MemoListResponseData;
import com.example.memoapplication.dto.response.MemoResponseData;
import com.example.memoapplication.errorcode.ErrorCode;
import com.example.memoapplication.exception.AlreadyExistException;
import com.example.memoapplication.exception.MemoNotFoundException;
import com.example.memoapplication.exception.UserNotFoundException;
import com.example.memoapplication.model.Memo;
import com.example.memoapplication.model.MemoLike;
import com.example.memoapplication.repository.LikeJpaRepository;
import com.example.memoapplication.repository.MemoJpaRepository;
import com.example.memoapplication.repository.UserJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void createMemo(MemoCreateDto memoCreateDto, UUID userId) {
        Memo newMemo = Memo.builder()
                .user(userJpaRepository.findUserById(userId))
                .title(memoCreateDto.getTitle())
                .content(memoCreateDto.getContent())
                .build();
        memoJpaRepository.save(newMemo);
    }

    //메모 전체 조회
    public MemoListResponseData getAllMemo(UUID userId) {
        if (memoJpaRepository.existsByUserId(userId)) {
            //userId 존재 시
            List<Memo> memoList = memoJpaRepository.findAllByUserId(userId);
            MemoListResponseData memoListResponseData = MemoListResponseData.builder().memoList(memoList).build();
            return memoListResponseData;
        }
        throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
    }

    //id로 특정 메모 조회
    public MemoResponseData getMemoById(UUID userId, UUID id) {
        if (memoJpaRepository.existsByUserId(userId)) {
            //userId 존재 시
            if (memoJpaRepository.existsById(id)) {
                //memoId 존재 시
                MemoResponseData memoResponseData = MemoResponseData.builder().memo(memoJpaRepository.findMemoById(id)).build();
                return memoResponseData;
            }
            throw new MemoNotFoundException(ErrorCode.MEMO_NOT_FOUND, "메모 ID가 일치하지 않아 메모 조회 실패");
        }
        throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
    }

    //id로 특정 메모 삭제
    public void deleteMemoById(UUID userId, UUID id) {
        if (memoJpaRepository.existsByUserId(userId)) {
            //userId 존재 시
            if (memoJpaRepository.existsById(id)) {
                memoJpaRepository.deleteById(id);
                return;
            }
            throw new MemoNotFoundException(ErrorCode.MEMO_NOT_FOUND, "메모 ID가 일치하지 않아 메모 삭제 실패");
        }
        throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
    }

    //id로 특정 메모 수정
    public void updateMemoById(MemoUpdateDto memoUpdateDto, UUID userId, UUID id) {
        if (memoJpaRepository.existsByUserId(userId)) {
            //userId 존재 시
            if (memoJpaRepository.existsById(id)) {
                //memoId 존재 시
                Memo memoToUpdate = memoJpaRepository.findMemoById(id);
                memoToUpdate.setTitle(memoUpdateDto.getTitle());
                memoToUpdate.setContent(memoUpdateDto.getContent());

                memoJpaRepository.save(memoToUpdate);
                return;
            }
            throw new MemoNotFoundException(ErrorCode.MEMO_NOT_FOUND, "메모 ID가 일치하지 않아 메모 수정 실패");
        }
        throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
    }

    //메모 like 기능 구현
    public void likeMemo(UUID userId, UUID id) {
        if (!likeJpaRepository.existsByUserId(userId)) {
            //userId 중복이 아닐 경우
            if (memoJpaRepository.existsById(id)) {
                //memoId 존재 시
                MemoLike like = MemoLike.builder()
                        .memo(memoJpaRepository.findMemoById(id))
                        .user(userJpaRepository.findUserById(userId))
                        .build();
                likeJpaRepository.save(like);
                return;
            }
            throw new MemoNotFoundException(ErrorCode.MEMO_NOT_FOUND, "메모 ID가 일치하지 않아 메모 좋아요 실패");
        }
        throw new AlreadyExistException(ErrorCode.ALREADY_EXIST, "이미 좋아요를 눌렀습니다.");
    }

    //likeList 조회
    public LikeListResponseData getLikeList(UUID id) {
        if (memoJpaRepository.existsById(id)) {
            //memoId 존재 시
            List<MemoLike> likeList = likeJpaRepository.findAllById(id);
            LikeListResponseData likeListResponseData = LikeListResponseData.builder()
                    .likeList(likeList)
                    .likeCount(likeList.size())
                    .build();
            return likeListResponseData;
        }
        throw new MemoNotFoundException(ErrorCode.MEMO_NOT_FOUND, "메모 ID가 일치하지 않아 메모 좋아요 리스트 조회 실패");
    }
}
