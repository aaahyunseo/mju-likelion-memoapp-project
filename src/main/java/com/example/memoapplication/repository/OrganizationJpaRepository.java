package com.example.memoapplication.repository;

import com.example.memoapplication.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrganizationJpaRepository extends JpaRepository<Organization, UUID> {
    Organization findOrganizationById(UUID id);

    boolean existsByName(String name);
}
