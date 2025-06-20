package com.example.prj1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {

    @GetMapping("write")
    public String write() {

        return "board/write";
    }
}
