package com.example.memoapplication.repository;

import com.example.memoapplication.model.Memo;
import com.example.memoapplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MemoJpaRepository extends JpaRepository<Memo, UUID> {
    List<Memo> findAllByUser(User user);

    boolean existsByUser(User user);

    Memo findMemoByUser(User user);

    Memo findMemoById(UUID id);
}
