package com.example.prj1.repository;

import com.example.prj1.dto.BoardListInfo;
import com.example.prj1.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    List<BoardListInfo> findAllBy();

    Page<BoardListInfo> findAllBy(Pageable pageable);
}