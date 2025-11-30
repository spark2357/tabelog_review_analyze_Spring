package com.example.reviewAnalyze.service;

import com.example.reviewAnalyze.dto.AnalyzedResultDto;
import com.example.reviewAnalyze.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class AnalyzeService {

    private final ResultService resultService;

    @Value("${fastapi.url}")
    private String FASTAPI_URL;

    public Long requestAnalyze(String url, Integer review_num, User user) {

        RestTemplate restTemplate = new RestTemplate();

        // 요청을 보내고 결과를 받는다.
        AnalyzedResultDto result = restTemplate.exchange(FASTAPI_URL, HttpMethod.POST, getHttpEntity(url, review_num), AnalyzedResultDto.class)
                .getBody();

        // TODO: result Null 처리하기

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