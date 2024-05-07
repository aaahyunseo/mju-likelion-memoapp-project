package com.example.memoapplication.service;

import com.example.memoapplication.dto.UserCreateDto;
import com.example.memoapplication.dto.UserLoginDto;
import com.example.memoapplication.dto.UserUpdateDto;
import com.example.memoapplication.model.User;
import com.example.memoapplication.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    //회원가입
    public void createUser(UserCreateDto userCreateDto) {
        UUID uuid = UUID.randomUUID();
        User user = User.builder()
                .id(uuid)
                .name(userCreateDto.getName())
                .email(userCreateDto.getEmail())
                .password(userCreateDto.getPassword())
                .build();
        userRepository.joinUser(user);
    }

    //회원탈퇴
    public void deleteUserById(UUID userId) {
        userRepository.deleteUserById(userId);
    }

    //회원정보 수정
    public void updateUserById(UserUpdateDto userUpdateDto, UUID userId) {
        User userToUpdate = userRepository.findById(userId);
        userToUpdate.setName(userUpdateDto.getName());
        userToUpdate.setEmail(userUpdateDto.getEmail());

        userRepository.updateUserById(userToUpdate);
    }

    //로그인
    public void login(UserLoginDto userLoginDto, UUID userId) {
        User userLogin = userRepository.findById(userId);
        userLogin.setEmail(userLoginDto.getEmail());
        userLogin.setPassword(userLoginDto.getPassword());

        userRepository.login(userLogin);
    }
}
