package com.example.reviewanalyze.Controller;
import com.example.reviewanalyze.Domain.ReviewResult;
import com.example.reviewanalyze.Domain.User;
import com.example.reviewanalyze.Dto.ReviewResultDto;
import com.example.reviewanalyze.Service.AnalyzeService;
import com.example.reviewanalyze.Service.ReviewResultService;
import com.example.reviewanalyze.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;

@Controller
@RequiredArgsConstructor
public class AnalyzeController {

    private final AnalyzeService analyzeService;
    private final UserService userService;
    private final ReviewResultService reviewResultService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/analyze")
    public String getAnalyzeInfo(){
        return "input";
    }

    @PostMapping("/analyze")
    public String analyze(@RequestParam String url,
                          @RequestParam(name="review_num", defaultValue="10") int review_num,
                          Model model,
                          Authentication authentication) {
        ReviewResultDto result = analyzeService.analyzeUrl(url, review_num);

        String username = authentication.getName();
        User currentUser = userService.findByUsername(username);

        ReviewResult savedResult = reviewResultService.saveReviewResultDto(currentUser, result);

        return "redirect:/result/" + savedResult.getId();
    }

    @GetMapping("/test_data")
    public String testData(Model model) {
        ReviewResultDto result = analyzeService.testUrl();
        model.addAttribute("result", result);
        return "result";
    }
}