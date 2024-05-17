package com.example.memoapplication.service;

import com.example.memoapplication.dto.request.OrganizationCreateDto;
import com.example.memoapplication.dto.request.OrganizationJoinDto;
import com.example.memoapplication.errorcode.ErrorCode;
import com.example.memoapplication.exception.AlreadyExistException;
import com.example.memoapplication.model.Organization;
import com.example.memoapplication.model.OrganizationUser;
import com.example.memoapplication.model.User;
import com.example.memoapplication.repository.OrganizationJpaRepository;
import com.example.memoapplication.repository.OrganizationUserJpaRepository;
import com.example.memoapplication.repository.UserJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class OrganizationService {

    private final OrganizationUserJpaRepository organizationUserJpaRepository;
    private final OrganizationJpaRepository organizationJpaRepository;
    private final UserJpaRepository userJpaRepository;

    //organization 생성
    public void createOrganization(OrganizationCreateDto organizationCreateDto) {
        if (!organizationJpaRepository.existsByName(organizationCreateDto.getName())) {
            //organization name이 존재하지 않을 경우
            Organization organization = Organization.builder()
                    .name(organizationCreateDto.getName())
                    .build();
            organizationJpaRepository.save(organization);
        }
        throw new AlreadyExistException(ErrorCode.ALREADY_EXIST, "이미 존재하는 organization 입니다.");
    }

    //organization 가입
    public void joinOrganization(OrganizationJoinDto organizationJoinDto, User user, UUID id) {
        Organization organization = organizationJpaRepository.findOrganizationById(id);
        //중복 가입 여부 확인
        if (organizationUserJpaRepository.existsByUserAndOrganization(user, organization)) {
            throw new AlreadyExistException(ErrorCode.ALREADY_EXIST, "이미 가입된 회원 입니다.");
        }
        OrganizationUser organizationUser = OrganizationUser.builder()
                .user(userJpaRepository.findUserById(user.getId()))
                .organization(organizationJpaRepository.findOrganizationById(id))
                .role(organizationJoinDto.getRole())
                .build();
        organizationUserJpaRepository.save(organizationUser);
    }

    //organization 탈퇴
    public void deleteOrganization(UUID id) {
        organizationUserJpaRepository.removeByOrganizationId(id);
    }
}
