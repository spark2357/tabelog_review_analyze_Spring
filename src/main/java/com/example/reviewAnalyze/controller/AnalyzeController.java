package com.example.reviewAnalyze.controller;

import com.example.reviewAnalyze.dto.displayDto.DisplayQueueDto;
import com.example.reviewAnalyze.entity.User;
import com.example.reviewAnalyze.service.AnalyzeService;
import com.example.reviewAnalyze.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AnalyzeController {

    private final AnalyzeService analyzeService;
    private final UserService userService;

    @GetMapping("/analyze")
    public String analyzeForm(Model model){
        DisplayQueueDto displayQueueDto = analyzeService.getQueueStatus();
        model.addAttribute("queueSize", displayQueueDto.queueSize());
        model.addAttribute("maxQueueSize", displayQueueDto.maxQueueSize());
        return "input";
    }

    @PostMapping("/analyze")
    public String analyze(@RequestParam String url,
                          @RequestParam(name="review_num", defaultValue="10") int review_num,
                          Authentication authentication) {

        // 로그인 유저 정보 가져오기
        String username = authentication.getName();
        User currentUser = userService.findByUsername(username);

        // analyzeService는 FastAPI와 통신을 담당함.
        analyzeService.requestAnalyze(url, review_num, currentUser);
        return "enqueue";
    }
}