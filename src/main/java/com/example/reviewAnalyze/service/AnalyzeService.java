package com.example.reviewAnalyze.service;

import com.example.reviewAnalyze.dto.analyzeDto.AnalyzedResultDto;
import com.example.reviewAnalyze.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class AnalyzeService {

    private final ResultService resultService;

    @Value("${fastapi.url}")
    private String FASTAPI_URL;

    public String requestAnalyze(String url, Integer review_num, User user) {
        RestTemplate restTemplate = new RestTemplate();
        AnalyzedResultDto result = restTemplate.exchange(FASTAPI_URL, HttpMethod.POST, getHttpEntity(url, review_num), AnalyzedResultDto.class)
                .getBody();

        return resultService.saveResult(user, result);
    }

    private HttpEntity<Map<String, Object>> getHttpEntity(String url, Integer review_num) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // FastAPI에 전달할 body
        Map<String, Object> request = new ConcurrentHashMap<>();
        request.put("url", url);
        request.put("review_num", review_num);

        return new HttpEntity<>(request, headers);
    }


}