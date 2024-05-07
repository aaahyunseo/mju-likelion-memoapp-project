package com.example.memoapplication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity(name = "organization")
public class Organization extends BaseEntity {
    @Setter
    @Column(length = 50, nullable = false)// 길이는 20자 이하이고, 비어있을 수 없다.
    private String name;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    // organization 필드를 기준으로 One To Many 관계를 맺는다.
    private List<OrganizationUser> organizationUsers;
}
