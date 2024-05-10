package com.example.memoapplication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "memo_like")
public class MemoLike extends BaseEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Memo memo;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;
}