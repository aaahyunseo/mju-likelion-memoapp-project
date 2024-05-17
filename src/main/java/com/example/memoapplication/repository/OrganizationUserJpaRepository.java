package com.example.memoapplication.repository;

import com.example.memoapplication.model.Organization;
import com.example.memoapplication.model.OrganizationUser;
import com.example.memoapplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrganizationUserJpaRepository extends JpaRepository<OrganizationUser, UUID> {
    void removeByOrganizationId(UUID id);

    boolean existsByUserAndOrganization(User user, Organization organization);
}
