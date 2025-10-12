package com.example.reviewanalyze.Service;

import com.example.reviewanalyze.Dto.ReviewResultDto;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AnalyzeService {
    private final String FASTAPI_URL = "http://127.0.0.1:8000/analyze"; // FastAPI 엔드포인트

    public ReviewResultDto analyzeUrl(String url, Integer review_num) {
        RestTemplate restTemplate = new RestTemplate();

        // FastAPI에 전달할 JSON body
        Map<String, Object> request = new HashMap<>();
        request.put("url", url);
        request.put("review_num", review_num);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        ResponseEntity<ReviewResultDto> response =
                restTemplate.exchange(FASTAPI_URL, HttpMethod.POST, entity, ReviewResultDto.class);
        return response.getBody();
    }

    public ReviewResultDto testUrl(){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(headers);

        ResponseEntity<ReviewResultDto> response =
                restTemplate.exchange("http://127.0.0.1:8000/test_data", HttpMethod.GET, entity, ReviewResultDto.class);
        return response.getBody();
    }
}