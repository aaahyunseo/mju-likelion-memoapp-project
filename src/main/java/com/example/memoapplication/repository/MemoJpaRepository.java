package com.example.memoapplication.repository;

import com.example.memoapplication.model.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MemoJpaRepository extends JpaRepository<Memo, UUID> {
    List<Memo> findAllByUserId(UUID userId);

    boolean existsByUserId(UUID userId);

    Memo findMemoById(UUID id);
}
