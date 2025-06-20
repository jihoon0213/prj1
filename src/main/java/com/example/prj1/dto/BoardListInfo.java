package com.example.prj1.dto;

import java.time.LocalDateTime;

public interface BoardListInfo {
    public Integer getId();

    public String getTitle();

    public String getWriter();

    public LocalDateTime getCreatedAt();
}