package com.example.reviewAnalyze.service;

import com.example.reviewAnalyze.dto.analyzeDto.AnalyzedResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerService {

    @Value("${fastapi.url}")
    private String FASTAPI_URL;
    private ScheduledExecutorService scheduler;
    private final ResultService resultService;

    public void startPolling() {
        if(scheduler == null || scheduler.isShutdown()){
            scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.scheduleAtFixedRate(() -> {
                AnalyzedResultDto result = pollResult();
                if(result == null && checkIsQueueEmpty()){
                    stopPolling();
                }else if(result != null){
                    resultService.saveResult(result);
                }
            }, 0, 20, TimeUnit.SECONDS);
        }
    }

    private void stopPolling() {
        if (scheduler != null && !scheduler.isShutdown()) {
            log.info("shutdown polling");
            scheduler.shutdown();
            scheduler.shutdownNow();
        }
    }

    private Boolean checkIsQueueEmpty(){
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response =
                restTemplate.exchange(
                        FASTAPI_URL + "/queue/status",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<Map<String, Object>>() {}
                ).getBody();
        if(response == null){
            throw new NullPointerException("대기열 길이 반환 오류");
        }
        Integer queueSize = (Integer) response.get("queue_size");
        return queueSize == 0;
    }

    private AnalyzedResultDto pollResult() {
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForEntity(FASTAPI_URL + "/result", AnalyzedResultDto.class)
                .getBody();
    }

}
