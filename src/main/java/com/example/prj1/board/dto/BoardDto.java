package com.example.prj1.board.dto;

import com.example.prj1.board.entity.Board;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Board}
 */
@Data
public class BoardDto implements Serializable {
    Integer id;
    String title;
    String content;
    String writer;
    LocalDateTime createdAt;
}