package com.example.reviewAnalyze.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public String handleEntityNotFound(EntityNotFoundException ex) {
        log.error("접근 권한이 없거나 존재하지 않는 분석 결과");
        log.error(ex.getMessage());
        return "error/404";
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public String handleHttpServerErrorException(HttpServerErrorException ex){
        log.error("분석 과정 오류");
        log.error(ex.getMessage());
        return "error/400";
    }

    @ExceptionHandler(ResourceAccessException.class)
    public String handleResourceAccessException(ResourceAccessException ex){
        log.error("분석 서버 연결 오류");
        log.error(ex.getMessage());
        return "error/500";
    }

    // 그 외 모든 예외 → 500 반환
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex) {
        log.error(ex.getMessage());
        return "error/500";
    }
}
