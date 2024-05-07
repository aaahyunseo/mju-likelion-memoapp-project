package com.example.memoapplication.repository;

import com.example.memoapplication.errorcode.ErrorCode;
import com.example.memoapplication.exception.AlreadyExistException;
import com.example.memoapplication.exception.LoginFalseException;
import com.example.memoapplication.exception.UserNotFoundException;
import com.example.memoapplication.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@AllArgsConstructor
public class UserRepository {
    private final UserJpaRepository userJpaRepository;

    //회원가입
    public void joinUser(User user) {
        if (userJpaRepository.existsByEmail(user.getEmail())) {
            //유저 Email이 이미 존재할 경우
            throw new AlreadyExistException(ErrorCode.ALREADY_EXIST, "Email 중복으로 회원가입 실패");
        }
        //새로운 유저일 경우
        userJpaRepository.save(user);
    }

    //회원탈퇴
    public void deleteUserById(UUID userId) {
        if (userJpaRepository.existsById(userId)) {
            //userId 존재 여부 확인
            userJpaRepository.deleteById(userId);
            return;
        }
        //userId가 없을 경우
        throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
    }

    //회원정보 수정
    public void updateUserById(User user) {
        if (userJpaRepository.existsById(user.getId())) {
            //userId 존재 여부 확인
            User userUpdate = userJpaRepository.findUserById(user.getId());
            userUpdate.setName(user.getName());     //이름 수정
            userUpdate.setEmail(user.getEmail());   //이메일 수정

            userJpaRepository.save(userUpdate);
            return;
        }
        //userId가 없을 경우
        throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
    }

    //로그인
    public User login(User user) {
        if (userJpaRepository.existsByEmail(user.getEmail())) {
            if (userJpaRepository.existsByPassword(user.getPassword())) {
                //유저 정보 반환?
                return userJpaRepository.getReferenceById(user.getId());
            }
            //password 불일치
            throw new LoginFalseException(ErrorCode.LOGIN_FALSE, "password가 일치하지 않습니다.");
        }
        //email 불일치
        throw new LoginFalseException(ErrorCode.LOGIN_FALSE, "email이 일치하지 않습니다.");
    }

    //userId와 일치하는 User 정보 반환
    public User findById(UUID id) {
        if (!userJpaRepository.existsById(id)) {
            //userId가 없을 경우
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        return userJpaRepository.findUserById(id);
    }
}