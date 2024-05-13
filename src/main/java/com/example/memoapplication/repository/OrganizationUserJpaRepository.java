package com.example.memoapplication.repository;

import com.example.memoapplication.model.OrganizationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrganizationUserJpaRepository extends JpaRepository<OrganizationUser, UUID> {
    boolean existsByOrganizationId(UUID id);

    void removeByOrganizationId(UUID id);

}
