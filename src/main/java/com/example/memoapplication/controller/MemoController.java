package com.example.memoapplication.controller;

import com.example.memoapplication.annotation.AuthenticatedUser;
import com.example.memoapplication.dto.request.MemoCreateDto;
import com.example.memoapplication.dto.request.MemoUpdateDto;
import com.example.memoapplication.dto.response.LikeListResponseData;
import com.example.memoapplication.dto.response.MemoListResponseData;
import com.example.memoapplication.dto.response.MemoResponseData;
import com.example.memoapplication.dto.response.ResponseDto;
import com.example.memoapplication.model.User;
import com.example.memoapplication.service.MemoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/memos")
public class MemoController {
    private final MemoService memoService;

    //메모 생성
    @PostMapping
    public ResponseEntity<ResponseDto<Void>> createMemo(@RequestBody @Valid MemoCreateDto memoCreateDto, @AuthenticatedUser User user) {
        memoService.createMemo(memoCreateDto, user);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "createMemo"), HttpStatus.CREATED);
    }

    //메모 전체 조회
    @GetMapping
    public ResponseEntity<ResponseDto<MemoListResponseData>> getAllMemo(@AuthenticatedUser User user) {
        MemoListResponseData memoListResponseData = memoService.getAllMemo(user);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "getAllMemo", memoListResponseData), HttpStatus.OK);
    }

    //memoId로 특정 메모 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<MemoResponseData>> getMemoById(@AuthenticatedUser User user, @PathVariable("id") UUID id) {
        MemoResponseData memoResponseData = memoService.getMemoById(user, id);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "getMemoById", memoResponseData), HttpStatus.OK);
    }

    //memoId로 특정 메모 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteMemoById(@AuthenticatedUser User user, @PathVariable("id") UUID id) {
        memoService.deleteMemoById(user, id);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "deleteMemoById"), HttpStatus.OK);
    }

    //memoId로 특정 메모 수정
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> updateMemoById(@RequestBody @Valid MemoUpdateDto memoUpdateDto, @AuthenticatedUser User user, @PathVariable("id") UUID id) {
        memoService.updateMemoById(memoUpdateDto, user, id);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "updateMemoById"), HttpStatus.OK);
    }

    //메모 좋아요 기능 구현
    @PostMapping("/{id}/likes")
    public ResponseEntity<ResponseDto<Void>> likeMemo(@AuthenticatedUser User user, @PathVariable("id") UUID id) {
        memoService.likeMemo(user, id);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "likeMemo complete!"), HttpStatus.CREATED);
    }

    //좋아요 정보 조회
    @GetMapping("/{id}/likes")
    public ResponseEntity<ResponseDto<LikeListResponseData>> getLikeList(@PathVariable("id") UUID id) {
        LikeListResponseData likeListResponseData = memoService.getLikeList(id);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "getLikeList", likeListResponseData), HttpStatus.OK);
    }
}