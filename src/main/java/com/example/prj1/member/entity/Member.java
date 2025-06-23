package com.example.prj1.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@Setter
@Getter
@ToString
public class Member {

    @Id
    private String id;
    private String password;
    private String nickName;
    private String info;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}