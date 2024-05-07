package com.example.memoapplication.service;

import com.example.memoapplication.dto.OrganizationJoinDto;
import com.example.memoapplication.model.Organization;
import com.example.memoapplication.repository.OrganizationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    //organization 가입
    public void joinOrganization(OrganizationJoinDto organizationJoinDto, UUID userId) {
        Organization organization = Organization.builder()
                .name(organizationJoinDto.getName())
                .build();
        organizationRepository.joinOrganization(organization, userId);
    }

    //organization 탈퇴
    public void deleteOrganization(UUID userId) {
        organizationRepository.deleteOrganization(userId);
    }
}
