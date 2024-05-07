package com.example.memoapplication.repository;

import com.example.memoapplication.model.MemoLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LikeJpaRepository extends JpaRepository<MemoLike, UUID> {
    int countAllBy();
}
