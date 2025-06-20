package com.example.prj1.controller;

import com.example.prj1.dto.BoardForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {

    @GetMapping("write")
    public String write() {

        return "board/write";
    }

    @PostMapping("write")
    public String writePost(BoardForm data) {
        System.out.println("data = " + data);

        return "board/write";
    }
}