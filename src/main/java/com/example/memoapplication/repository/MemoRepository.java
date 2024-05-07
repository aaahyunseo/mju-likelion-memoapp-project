package com.example.memoapplication.repository;

import com.example.memoapplication.errorcode.ErrorCode;
import com.example.memoapplication.exception.AlreadyExistException;
import com.example.memoapplication.exception.MemoNotFoundException;
import com.example.memoapplication.exception.UserNotFoundException;
import com.example.memoapplication.model.Memo;
import com.example.memoapplication.model.MemoLike;
import com.example.memoapplication.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class MemoRepository {

    private MemoJpaRepository memoJpaRepository;
    private UserJpaRepository userJpaRepository;
    private LikeJpaRepository likeJpaRepository;

    //새로운 메모 생성
    public void createMemo(Memo memo) {
        if (memoJpaRepository.existsById(memo.getId())) {
            //memoId 존재 여부 확인
            throw new AlreadyExistException(ErrorCode.ALREADY_EXIST, "메모 ID 중복으로 메모 생성에 실패하였습니다.");
        }
        //새로운 메모일 경우
        memoJpaRepository.save(memo);
    }

    //전체 메모 조회
    public List<Memo> getAllMemo(UUID userId) {
        if (userJpaRepository.existsById(userId)) {
            //유저 ID 일치
            return memoJpaRepository.findMemosByUserId(userId);
        }
        //유저를 찾지 못했을 경우 예외처리
        throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
    }

    //특정 메모 조회
    public Memo getMemoById(UUID userId, UUID id) {
        if (userJpaRepository.existsById(userId)) {
            //유저 ID 일치
            if (memoJpaRepository.existsById(id)) {
                //메모 ID 일치
                return memoJpaRepository.findMemoById(id);
            }
            throw new MemoNotFoundException(ErrorCode.MEMO_NOT_FOUND, "해당 메모 ID가 존재하지 않아 메모 조회 실패.");
        }
        //유저를 찾지 못했을 경우 예외처리
        throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
    }

    //특정 메모 삭제
    public void deleteMemoById(UUID userId, UUID id) {
        if (userJpaRepository.existsById(userId)) {
            //유저 ID 일치
            if (memoJpaRepository.existsById(id)) {
                //메모 ID 일치
                memoJpaRepository.deleteById(id);
                return;
            }
            throw new MemoNotFoundException(ErrorCode.MEMO_NOT_FOUND, "해당 메모 ID가 존재하지 않아 메모 삭제 실패.");
        }
        //유저를 찾지 못했을 경우 예외처리
        throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
    }

    //특정 메모 수정
    public void updateMemoById(Memo memo, UUID userId) {
        if (userJpaRepository.existsById(userId)) {
            //유저 ID 일치
            if (memoJpaRepository.existsById(memo.getId())) {
                //메모 ID 일치
                Memo memoUpdate = memoJpaRepository.findMemoById(memo.getId());
                memoUpdate.setContent(memo.getContent());     //메모 내용 수정
                memoUpdate.setTitle(memo.getTitle());   //메모 제목 수정

                memoJpaRepository.save(memoUpdate);
                return;
            }
            throw new MemoNotFoundException(ErrorCode.MEMO_NOT_FOUND, "해당 메모 ID가 존재하지 않아 메모 수정 실패.");
        }
        //유저를 찾지 못했을 경우 예외처리
        throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
    }

    //메모 좋아요 기능 구현
    public void likeMemo(UUID userId, UUID id) {
        User user = userJpaRepository.findUserById(userId);
        Memo memo = memoJpaRepository.findMemoById(id);
        int count = likeJpaRepository.countAllBy();   //좋아요 수

        MemoLike like = MemoLike.builder()
                .user(user)
                .count(count + 1)
                .memo(memo)
                .build();
        likeJpaRepository.save(like);
    }

    //메모 좋아요 목록
    public List<MemoLike> getLikeList(UUID memoId) {
        List<MemoLike> memoLikeList = new ArrayList<>();
        for (MemoLike memoLike : likeJpaRepository.findAll()) {
            if (memoLike.getMemo().getId().equals(memoId)) {
                memoLikeList.add(memoLike);
            }
        }
        return memoLikeList;
    }
}
