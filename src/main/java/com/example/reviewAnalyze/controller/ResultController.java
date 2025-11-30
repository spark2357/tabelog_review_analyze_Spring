package com.example.reviewAnalyze.controller;

import com.example.reviewAnalyze.dto.displayDto.DisplayResultDto;
import com.example.reviewAnalyze.entity.LabelType;
import com.example.reviewAnalyze.entity.Place;
import com.example.reviewAnalyze.entity.User;
import com.example.reviewAnalyze.service.ResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;

    @GetMapping("/result/{placeId}")
    public String showResult(@PathVariable Long placeId, Model model) {


        DisplayResultDto displayResultDto = resultService.getReviewResult(placeId);

        model.addAttribute("place", displayResultDto.place());
        model.addAttribute("labeledKeywords", displayResultDto.labeledKeywords());
        model.addAttribute("reviews", displayResultDto.reviews());
        model.addAttribute("labelTypes", LabelType.values());
        return "result";
    }


    @GetMapping("/my/results")
    public String showResultList(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "id") String sortBy,
                                 @RequestParam(defaultValue = "desc") String direction, Model model, Authentication authentication) {

        User userDetails = (User)authentication.getPrincipal();
        Long userId = userDetails.getId();
        log.info("userId={}", userId);

        Page<Place> results = resultService.getResultListByUserId(userId, page, size, sortBy, direction);

        model.addAttribute("results", results.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", results.getTotalPages());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("direction", direction);
        return "list";
    }
}
