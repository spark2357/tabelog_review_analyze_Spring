package com.example.reviewanalyze.Controller;

import com.example.reviewanalyze.Domain.ReviewResult;
import com.example.reviewanalyze.Service.ReviewResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ResultController {

    private final ReviewResultService reviewResultService;

    @GetMapping("/result/{id}")
    public String showResult(@PathVariable Long id, Model model) {
        ReviewResult reviewResult = reviewResultService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰 결과를 찾을 수 없습니다."));

        model.addAttribute("result", reviewResult);
        return "result"; // result.html 렌더링
    }

    @GetMapping("/home")
    public String home(Model model) {
//        model.addAttribute("reviewResults", reviewResultService.findAll());
        return "home";
    }
}
