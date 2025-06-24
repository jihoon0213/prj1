package com.example.prj1.board.dto;

import com.example.prj1.board.entity.Board;
import com.example.prj1.member.dto.MemberDto;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Board}
 */
//@Value
@Data
public class BoardDto implements Serializable {
    Integer id;
    String title;
    String content;
    MemberDto writer;
    LocalDateTime createdAt;
}