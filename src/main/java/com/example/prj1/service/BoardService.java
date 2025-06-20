package com.example.prj1.service;

import com.example.prj1.dto.BoardForm;
import com.example.prj1.entity.Board;
import com.example.prj1.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void add(BoardForm formData) {
        Board board = new Board();
        board.setTitle(formData.getTitle());
        board.setContent(formData.getContent());
        board.setWriter(formData.getWriter());

        boardRepository.save(board);


    }

    public List<Board> list() {
        List<Board> list = boardRepository.findAll();


        return list;
    }
}
