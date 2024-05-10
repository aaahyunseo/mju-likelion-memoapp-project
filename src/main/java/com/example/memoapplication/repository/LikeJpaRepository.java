package com.example.memoapplication.repository;

import com.example.memoapplication.model.MemoLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LikeJpaRepository extends JpaRepository<MemoLike, UUID> {
    List<MemoLike> findAllById(UUID id);

    boolean existsByUserId(UUID userId);
}
