package com.example.memoapplication.service;

import com.example.memoapplication.dto.request.UserCreateDto;
import com.example.memoapplication.dto.request.UserLoginDto;
import com.example.memoapplication.dto.request.UserUpdateDto;
import com.example.memoapplication.errorcode.ErrorCode;
import com.example.memoapplication.exception.AlreadyExistException;
import com.example.memoapplication.exception.LoginFalseException;
import com.example.memoapplication.exception.UserNotFoundException;
import com.example.memoapplication.model.User;
import com.example.memoapplication.repository.UserJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class UserService {
    private final UserJpaRepository userJpaRepository;

    //회원가입
    public void createUser(UserCreateDto userCreateDto) {
        if (!userJpaRepository.existsByEmail(userCreateDto.getEmail())) {
            //등록되지 않은 이메일
            User user = User.builder()
                    .name(userCreateDto.getName())
                    .email(userCreateDto.getEmail())
                    .password(userCreateDto.getPassword())
                    .build();
            userJpaRepository.save(user);
            return;
        }
        throw new AlreadyExistException(ErrorCode.ALREADY_EXIST, "이미 존재하는 회원입니다.");
    }

    //회원탈퇴
    public void deleteUserById(UUID userId) {

        if (userJpaRepository.existsById(userId)) {
            //userId 존재 시
            userJpaRepository.deleteById(userId);
            return;
        }
        throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
    }

    //회원정보 수정
    public void updateUserById(UserUpdateDto userUpdateDto, UUID userId) {

        if (userJpaRepository.existsById(userId)) {
            //userId 존재 시
            User userToUpdate = userJpaRepository.findUserById(userId);
            userToUpdate.setName(userUpdateDto.getName());
            userToUpdate.setEmail(userUpdateDto.getEmail());

            userJpaRepository.save(userToUpdate);
            return;
        }
        throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
    }

    //로그인
    public void login(UserLoginDto userLoginDto, UUID userId) {
        if (userJpaRepository.existsById(userId)) {
            //userId 존재 시
            if (userJpaRepository.existsByEmail(userLoginDto.getEmail())) {
                //email 일치 시
                if (userJpaRepository.existsByPassword(userLoginDto.getPassword())) {
                    //password 일치 시
                    return;
                }
                throw new LoginFalseException(ErrorCode.LOGIN_FALSE, "password가 일치하지 않습니다.");
            }
            throw new LoginFalseException(ErrorCode.LOGIN_FALSE, "email이 일치하지 않습니다.");
        }
        throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
    }
}
