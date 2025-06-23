package com.example.prj1.member.dto;

import lombok.Data;

@Data
public class MemberForm {
    private String id;
    private String nickName;
    private String password;
    private String info;
}