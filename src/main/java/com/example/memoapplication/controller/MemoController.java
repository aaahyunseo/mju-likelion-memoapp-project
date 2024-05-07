package com.example.memoapplication.controller;

import com.example.memoapplication.dto.MemoCreateDto;
import com.example.memoapplication.dto.MemoUpdateDto;
import com.example.memoapplication.dto.response.LikeListResponseData;
import com.example.memoapplication.dto.response.MemoListResponseData;
import com.example.memoapplication.dto.response.MemoResponseData;
import com.example.memoapplication.dto.response.ResponseDto;
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
    public ResponseEntity<ResponseDto<Void>> createMemo(@RequestBody @Valid MemoCreateDto memoCreateDto, @RequestHeader("user-id") UUID userId) {
        memoService.createMemo(memoCreateDto, userId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "createMemo"), HttpStatus.CREATED);
    }

    //메모 전체 조회
    @GetMapping
    public ResponseEntity<ResponseDto<MemoListResponseData>> getAllMemo(@RequestHeader("user-id") UUID userId) {
        MemoListResponseData memoListResponseData = memoService.getAllMemo(userId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "getAllMemo", memoListResponseData), HttpStatus.OK);
    }

    //memoId로 특정 메모 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<MemoResponseData>> getMemoById(@RequestHeader("user-id") UUID userId, @PathVariable("id") UUID id) {
        MemoResponseData memoResponseData = memoService.getMemoById(userId, id);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "getMemoById", memoResponseData), HttpStatus.OK);
    }

    //memoId로 특정 메모 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteMemoById(@RequestHeader("user-id") UUID userId, @PathVariable("id") UUID id) {
        memoService.deleteMemoById(userId, id);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "deleteMemoById"), HttpStatus.OK);
    }

    //memoId로 특정 메모 수정
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> updateMemoById(@RequestBody @Valid MemoUpdateDto memoUpdateDto, @RequestHeader("user-id") UUID userId, @PathVariable("id") UUID id) {
        memoService.updateMemoById(memoUpdateDto, id, userId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "updateMemoById"), HttpStatus.OK);
    }

    //메모 좋아요 기능 구현
    @PostMapping("/{id}/likes")
    public ResponseEntity<ResponseDto<Void>> likeMemo(@RequestHeader("user-id") UUID userId, @PathVariable("id") UUID id) {
        memoService.likeMemo(userId, id);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "likeMemo complete!"), HttpStatus.OK);
    }

    //좋아요 정보 조회
    @GetMapping("/{id}/likes")
    public ResponseEntity<ResponseDto<LikeListResponseData>> getLikeList(@PathVariable("id") UUID id) {
        LikeListResponseData likeListResponseData = memoService.getLikeList(id);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "getLikeList", likeListResponseData), HttpStatus.OK);
    }
}