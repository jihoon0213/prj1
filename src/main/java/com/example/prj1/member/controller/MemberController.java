package com.example.prj1.member.controller;

import com.example.prj1.member.dto.MemberDto;
import com.example.prj1.member.dto.MemberForm;
import com.example.prj1.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("signup")
    public String signupForm() {
        return "member/signup";
    }

    @PostMapping("signup")
    public String signup(MemberForm data, RedirectAttributes rttr) {
        try {
            // service
            memberService.add(data);


            rttr.addFlashAttribute("alert",
                    Map.of("code", "success", "message", "회원 가입되었습니다."));

            return "redirect:/board/list";
        } catch (DuplicateKeyException e) {
            rttr.addFlashAttribute("alert",
                    Map.of("code", "warning", "message", e.getMessage()));

            rttr.addFlashAttribute("member", data);

            return "redirect:/member/signup";
        }
    }

    @GetMapping("list")
    public String list(Model model) {
        model.addAttribute("memberList", memberService.list());

        return "member/list";
    }

    @GetMapping("view")
    public String view(String id,
                       @SessionAttribute(value = "loggedInUser", required = false)
                       MemberDto user,
                       Model model,
                       RedirectAttributes rttr) {

        MemberDto member = memberService.get(id);

        if (user != null) {
            if (member.getId().equals(user.getId())) {
                model.addAttribute("member", member);

                return "member/view";
            }
        }
        rttr.addFlashAttribute("alert",
                Map.of("code", "warning", "message", "권한이 없습니다."));

        return "redirect:/board/list";

    }

    @PostMapping("remove")
    public String remove(MemberForm data,
                         @SessionAttribute(value = "loggedInUser", required = false)
                         MemberDto user,
                         RedirectAttributes rttr) {
        // TODO : 작성한 글이 있으면 탈퇴 안됨
        boolean result = memberService.remove(data, user);

        if (result) {
            rttr.addFlashAttribute("alert",
                    Map.of("code", "danger", "message", data.getId() + "님 탈퇴 되었습니다."));

            return "redirect:/board/list";
        } else {
            rttr.addFlashAttribute("alert",
                    Map.of("code", "danger", "message", "암호가 일치하지 않습니다."));

            rttr.addAttribute("id", data.getId());

            return "redirect:/member/view";
        }
    }

    @GetMapping("edit")
    public String edit(String id,
                       @SessionAttribute(value = "loggedInUser", required = false)
                       MemberDto user,
                       Model model,
                       RedirectAttributes rttr) {
        MemberDto member = memberService.get(id);
        if (user != null) {
            if (member.getId().equals(user.getId())) {
                model.addAttribute("member", member);
                return "member/edit";
            }
        }
        rttr.addFlashAttribute("alert",
                Map.of("code", "warning", "message", "권한이 없습니다."));
        return "redirect:/board/list";
    }

    @PostMapping("edit")
    public String edit(MemberForm data,
                       @SessionAttribute(value = "loggedInUser", required = false)
                       MemberDto user,
                       HttpSession session,
                       RedirectAttributes rttr) {


        boolean result = memberService.update(data, user, session);

        if (result) {

            rttr.addFlashAttribute("alert",
                    Map.of("code", "success", "message", "회원 정보가 변경되었습니다."));

            rttr.addAttribute("id", data.getId());
            return "redirect:/member/view";
        } else {
            rttr.addAttribute("id", data.getId());
            rttr.addFlashAttribute("alert",
                    Map.of("code", "warning", "message", "암호가 일치하지 않습니다."));

            return "redirect:/member/edit";
        }

    }

    @PostMapping("changePw")
    public String changePassword(String id,
                                 String oldPassword,
                                 String newPassword,
                                 @SessionAttribute(value = "loggedInUser", required = false)
                                 MemberDto user,
                                 RedirectAttributes rttr) {

        if (user != null && user.getId().equals(id)) {
            boolean result = memberService.updatePassword(id, oldPassword, newPassword);

            if (result) {
                rttr.addFlashAttribute("alert",
                        Map.of("code", "success", "message", "암호가 변경되었습니다."));
            } else {
                rttr.addFlashAttribute("alert",
                        Map.of("code", "warning", "message", "암호가 일치하지 않습니다.."));
            }
        }

        rttr.addAttribute("id", id);
        return "redirect:/member/edit";

    }


    @GetMapping("login")
    public String loginForm() {

        return "member/login";
    }

    @PostMapping("login")
    public String loginProcess(String id, String password,
                               HttpSession session,
                               RedirectAttributes rttr) {

        boolean result = memberService.login(id, password, session);

        if (result) {
            rttr.addFlashAttribute("alert",
                    Map.of("code", "success",
                            "message", "로그인 되었습니다."));
            // 로그인 성공
            return "redirect:/board/list";
        } else {
            rttr.addFlashAttribute("alert",
                    Map.of("code", "warning",
                            "message", "아이디/패스워드가 일치하지 않습니다."));
            rttr.addFlashAttribute("id", id);

            // 로그인 실패
            return "redirect:/member/login";
        }

    }

    @RequestMapping("logout")
    public String logout(HttpSession session, RedirectAttributes rttr) {
        session.invalidate();

        rttr.addFlashAttribute("alert",
                Map.of("code", "success",
                        "message", "로그아웃 되었습니다."));

        return "redirect:/board/list";
    }
}