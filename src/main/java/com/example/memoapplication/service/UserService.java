package com.example.memoapplication.service;

import com.example.memoapplication.dto.request.UserCreateDto;
import com.example.memoapplication.dto.request.UserUpdateDto;
import com.example.memoapplication.errorcode.ErrorCode;
import com.example.memoapplication.exception.AlreadyExistException;
import com.example.memoapplication.model.User;
import com.example.memoapplication.repository.UserJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void deleteUserById(User user) {
        userJpaRepository.deleteById(user.getId());
    }

    //회원정보 수정
    public void updateUserById(UserUpdateDto userUpdateDto, User user) {
        User userToUpdate = userJpaRepository.findUserById(user.getId());
        userToUpdate.setName(userUpdateDto.getName());
        userToUpdate.setEmail(userUpdateDto.getEmail());

        userJpaRepository.save(userToUpdate);
    }
}
