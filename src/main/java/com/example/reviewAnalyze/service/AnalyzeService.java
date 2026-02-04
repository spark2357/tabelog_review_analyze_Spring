package com.example.reviewAnalyze.service;

import com.example.reviewAnalyze.dto.analyzeDto.AnalyzedResultDto;
import com.example.reviewAnalyze.dto.displayDto.DisplayQueueDto;
import com.example.reviewAnalyze.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyzeService {

    private final ResultService resultService;
    private final SchedulerService schedulerService;

    @Value("${fastapi.url}")
    private String FASTAPI_URL;

    public void requestAnalyze(String url, Integer review_num, User user) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(FASTAPI_URL + "/enqueue", getHttpEntity(user.getId(), url, review_num), String.class);
        if(response.getStatusCode().is2xxSuccessful()) {
            log.info("enqueue 성공"+response.getStatusCode().toString());
            schedulerService.startPolling();
        } else{
            throw new RuntimeException("대기열 추가 실패");
        }
    }

    public DisplayQueueDto getQueueStatus(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(FASTAPI_URL + "/queue/status", DisplayQueueDto.class).getBody();
    }

    private HttpEntity<Map<String, Object>> getHttpEntity(Long user_id, String url, Integer review_num) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // FastAPI에 전달할 body
        Map<String, Object> request = new ConcurrentHashMap<>();
        request.put("user_id", user_id.toString());
        request.put("url", url);
        request.put("review_num", review_num);

        return new HttpEntity<>(request, headers);
    }
}