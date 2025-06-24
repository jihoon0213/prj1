package com.example.prj1.board.controller;

import com.example.prj1.board.dto.BoardForm;
import com.example.prj1.board.service.BoardService;
import com.example.prj1.member.dto.MemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("write")
    public String writeForm(HttpSession session, RedirectAttributes rttr) {
        Object user = session.getAttribute("loggedInUser");

        if (user != null) {
            // 로그인 됐을 때
            return "board/write";
        } else {
            // 로그인 안됐을 때
            rttr.addFlashAttribute("alert",
                    Map.of("code", "warning",
                            "message", "로그인 후 글을 작성해주세요."));

            return "redirect:/member/login";
        }

    }

    @PostMapping("write")
    public String writePost(BoardForm data,
                            @SessionAttribute(name = "loggedInUser", required = false)
                            MemberDto user,
                            RedirectAttributes rttr) {


        if (user != null) {
            boardService.add(data, user);

            rttr.addFlashAttribute("alert",
                    Map.of("code", "primary", "message", "새 게시물이 등록되었습니다."));

            return "redirect:/board/list";
        } else {

            return "redirect:/member/login";
        }

    }

    @GetMapping("list")
    public String list(
            @RequestParam(defaultValue = "1")
            Integer page,
            @RequestParam(defaultValue = "")
            String keyword,
            Model model) {

        var result = boardService.list(page, keyword);

//        model.addAttribute("boardList", result);
        model.addAllAttributes(result);

        return "board/list";
    }

    @GetMapping("view")
    public String view(Integer id, Model model) {

        // service에게 일 시키고
        var dto = boardService.get(id);

        // model에 넣고
        model.addAttribute("board", dto);

        // view로 forward
        return "board/view";
    }

    @PostMapping("remove")
    public String remove(Integer id,
                         @SessionAttribute(value = "loggedInUser", required = false)
                         MemberDto user,
                         RedirectAttributes rttr) {
        boolean result = boardService.remove(id, user);

        if (result) {
            rttr.addFlashAttribute("alert",
                    Map.of("code", "danger", "message", id + "번 게시물이 삭제 되었습니다."));
            return "redirect:/board/list";
        } else {
            rttr.addFlashAttribute("alert",
                    Map.of("code", "danger", "message", id + "번 게시물이 삭제 되지 않았습니다."));
            rttr.addAttribute("id", id);
            return "redirect:/board/view";
        }


    }

    @GetMapping("edit")
    public String edit(Integer id, Model model) {
        var dto = boardService.get(id);
        model.addAttribute("board", dto);
        return "board/edit";
    }

    @PostMapping("edit")
    public String editPost(BoardForm data,
                           @SessionAttribute(value = "loggedInUser", required = false)
                           MemberDto user,
                           RedirectAttributes rttr) {
        boolean result = boardService.update(data, user);

        if (result) {
            rttr.addFlashAttribute("alert",
                    Map.of("code", "success", "message",
                            data.getId() + "번 게시물이 수정되었습니다."));

        } else {
            rttr.addFlashAttribute("alert",
                    Map.of("code", "danger", "message",
                            data.getId() + "번 게시물이 수정되지 않았습니다."));
        }

        rttr.addAttribute("id", data.getId());

        return "redirect:/board/view";
    }
}