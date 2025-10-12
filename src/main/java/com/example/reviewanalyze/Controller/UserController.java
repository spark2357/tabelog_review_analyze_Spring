package com.example.reviewanalyze.Controller;

import com.example.reviewanalyze.Dto.UserDto;
import com.example.reviewanalyze.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /** 회원가입 페이지 (GET 요청) */
    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "signup"; // signup.html 렌더링
    }

    /** 회원가입 처리 (POST 요청) */
    @PostMapping("/register")
    public String register(@ModelAttribute("user") UserDto userDto, Model model) {
        try {
            userService.register(userDto);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "signup"; // 에러 시 다시 회원가입 페이지로
        }

        // 회원가입 성공 시 로그인 페이지로 이동
        return "redirect:/login";
    }
    @GetMapping("/login")
    public String loginForm() {
        return "login"; // login.html
    }
}