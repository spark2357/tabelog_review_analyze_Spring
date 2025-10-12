package com.example.reviewanalyze.Controller;

import com.example.reviewanalyze.Domain.ReviewResult;
import com.example.reviewanalyze.Domain.User;
import com.example.reviewanalyze.Dto.KeywordDto;
import com.example.reviewanalyze.Service.ReviewResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ResultController {

    private final ReviewResultService reviewResultService;

    @GetMapping("/result/{id}")
    public String showResult(@PathVariable Long id, Model model) {
        ReviewResult reviewResult = reviewResultService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰 결과를 찾을 수 없습니다."));

        Map<String, List<KeywordDto>> groupedEntities = reviewResultService.getGroupEntitiesByLabel(reviewResult);

        model.addAttribute("groupedEntities", groupedEntities);
        model.addAttribute("result", reviewResult);
        return "result";
    }

    @GetMapping("/my/results")
    public String home(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       @RequestParam(defaultValue = "id") String sortBy,
                       @RequestParam(defaultValue = "desc") String direction,Model model, Authentication authentication) {
        User userDetails = (User)authentication.getPrincipal();
        Long userId = userDetails.getId();
        System.out.println(userId);
        Page<ReviewResult> results = reviewResultService.getReviewResultsByUserId(userId, page, size, sortBy, direction);
        System.out.println(results.getTotalElements());
        model.addAttribute("results", results.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", results.getTotalPages());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("direction", direction);
        return "results";
    }
}
