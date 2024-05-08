package com.example.memoapplication.service;

import com.example.memoapplication.dto.OrganizationJoinDto;
import com.example.memoapplication.model.Organization;
import com.example.memoapplication.model.OrganizationUser;
import com.example.memoapplication.repository.OrganizationRepository;
import com.example.memoapplication.repository.UserJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final UserJpaRepository userJpaRepository;

    //organization 가입
    public void joinOrganization(OrganizationJoinDto organizationJoinDto, UUID userId) {
        Organization organization = Organization.builder()
                .name(organizationJoinDto.getName())
                .build();

        OrganizationUser organizationUser = OrganizationUser.builder()
                .user(userJpaRepository.findUserById(userId))
                .organization(organization)
                .role(organizationJoinDto.getRole())
                .build();
        organizationRepository.joinOrganization(organizationUser, userId);
    }

    //organization 탈퇴
    public void deleteOrganization(UUID userId) {
        organizationRepository.deleteOrganization(userId);
    }
}
