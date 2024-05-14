package com.example.memoapplication.controller;

import com.example.memoapplication.annotation.AuthenticatedUser;
import com.example.memoapplication.authentication.JwtTokenProvider;
import com.example.memoapplication.dto.request.UserCreateDto;
import com.example.memoapplication.dto.request.UserUpdateDto;
import com.example.memoapplication.dto.response.ResponseDto;
import com.example.memoapplication.model.User;
import com.example.memoapplication.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    //회원가입
    @PostMapping
    public ResponseEntity<ResponseDto<Void>> joinUser(@RequestBody @Valid UserCreateDto userCreateDto) {
        userService.createUser(userCreateDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "sign Up"), HttpStatus.CREATED);
    }

    //회원탈퇴
    @DeleteMapping
    public ResponseEntity<ResponseDto<Void>> deleteUserById(@AuthenticatedUser User user) {
        userService.deleteUserById(user);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "delete User"), HttpStatus.OK);
    }

    //회원정보 수정
    @PatchMapping
    public ResponseEntity<ResponseDto<Void>> updateUserById(@RequestBody @Valid UserUpdateDto userUpdateDto, @AuthenticatedUser User user) {
        userService.updateUserById(userUpdateDto, user);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "update User"), HttpStatus.OK);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<ResponseDto<Void>> login(@RequestHeader("user-id") UUID userId,
                                                   HttpServletResponse response) {

        String payload = userId.toString();     //user의 id를 payload로 사용
        //AccessToken 생성
        String accessToken = jwtTokenProvider.createToken(payload);
        //쿠키 생성
        ResponseCookie cookie = ResponseCookie.from("AccessToken", accessToken)
                .maxAge(Duration.ofMillis(1800000))// 30분
                .path("/")// 모든 경로에서 접근 가능
                .build();
        //HTTP response 헤더에 생성한 쿠키 포함
        response.addHeader("set-cookie", cookie.toString());
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "AccessToken=" + accessToken), HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<ResponseDto<Void>> test(@AuthenticatedUser User user) {
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, user.getName()), HttpStatus.OK);
    }
}
