package com.example.memoapplication.controller;

import com.example.memoapplication.dto.request.UserCreateDto;
import com.example.memoapplication.dto.request.UserLoginDto;
import com.example.memoapplication.dto.request.UserUpdateDto;
import com.example.memoapplication.dto.response.ResponseDto;
import com.example.memoapplication.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    //회원가입
    @PostMapping
    public ResponseEntity<ResponseDto<Void>> joinUser(@RequestBody @Valid UserCreateDto userCreateDto) {
        userService.createUser(userCreateDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "sign Up"), HttpStatus.CREATED);
    }

    //회원탈퇴
    @DeleteMapping
    public ResponseEntity<ResponseDto<Void>> deleteUserById(@RequestHeader("user-id") UUID userId) {
        userService.deleteUserById(userId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "delete User"), HttpStatus.OK);
    }

    //회원정보 수정
    @PatchMapping
    public ResponseEntity<ResponseDto<Void>> updateUserById(@RequestBody @Valid UserUpdateDto userUpdateDto, @RequestHeader("user-id") UUID userId) {
        userService.updateUserById(userUpdateDto, userId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "update User"), HttpStatus.OK);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<ResponseDto<Void>> login(@RequestBody @Valid UserLoginDto userLoginDto, @RequestHeader("user-id") UUID userId) {
        userService.login(userLoginDto, userId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "login success!"), HttpStatus.OK);
    }
}
