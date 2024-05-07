package com.example.memoapplication.repository;

import com.example.memoapplication.errorcode.ErrorCode;
import com.example.memoapplication.exception.UserNotFoundException;
import com.example.memoapplication.model.Organization;
import com.example.memoapplication.model.OrganizationUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@AllArgsConstructor
public class OrganizationRepository {
    private final UserJpaRepository userJpaRepository;
    private final OrganizationUserJpaRepository organizationUserJpaRepository;

    //organization 가입
    public void joinOrganization(Organization organization, UUID userId) {
        if (userJpaRepository.existsById(userId)) {
            //유저 ID 존재
            OrganizationUser organizationUser = OrganizationUser.builder()
                    .user(userJpaRepository.findUserById(userId))
                    .organization(organization)
                    .build();
            organizationUserJpaRepository.save(organizationUser);
        }
        throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
    }

    //organization 탈퇴
    public void deleteOrganization(UUID userId) {
        if (organizationUserJpaRepository.existsByUserId(userId)) {
            //유저 ID 존재
            organizationUserJpaRepository.deleteByUserId(userId);
            return;
        }
        throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
    }
}
