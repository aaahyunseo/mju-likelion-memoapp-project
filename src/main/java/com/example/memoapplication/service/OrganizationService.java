package com.example.memoapplication.service;

import com.example.memoapplication.dto.request.OrganizationCreateDto;
import com.example.memoapplication.dto.request.OrganizationJoinDto;
import com.example.memoapplication.errorcode.ErrorCode;
import com.example.memoapplication.exception.AlreadyExistException;
import com.example.memoapplication.exception.OrganizationNotFoundException;
import com.example.memoapplication.exception.UserNotFoundException;
import com.example.memoapplication.model.Organization;
import com.example.memoapplication.model.OrganizationUser;
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
            //organization이 존재하지 않을 경우
            Organization organization = Organization.builder()
                    .name(organizationCreateDto.getName())
                    .build();
            organizationJpaRepository.save(organization);
            return;
        }
        throw new AlreadyExistException(ErrorCode.ALREADY_EXIST, "이미 존재하는 organization 입니다.");
    }

    //organization 가입
    public void joinOrganization(OrganizationJoinDto organizationJoinDto, UUID userId, UUID id) {
        if (organizationJpaRepository.existsById(id)) {
            //organization이 존재할 경우
            OrganizationUser organizationUser = OrganizationUser.builder()
                    .user(userJpaRepository.findUserById(userId))
                    .organization(organizationJpaRepository.findOrganizationById(id))
                    .role(organizationJoinDto.getRole())
                    .build();
            organizationUserJpaRepository.save(organizationUser);
            return;
        }
        throw new OrganizationNotFoundException(ErrorCode.ORGANIZATION_NOT_FOUND);
    }

    //organization 탈퇴
    public void deleteOrganization(UUID userId, UUID id) {
        if (organizationUserJpaRepository.existsByUserId(userId)) {
            //userId 존재 시
            if (organizationUserJpaRepository.existsByOrganizationId(id)) {
                //organization 존재 시
                organizationUserJpaRepository.removeByOrganizationId(id);
                return;
            }
            throw new OrganizationNotFoundException(ErrorCode.ORGANIZATION_NOT_FOUND);
        }
        throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
    }
}
