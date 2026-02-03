package com.example.reviewAnalyze.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // 엔티티를 못 찾았을 때 → 404 반환
    @ExceptionHandler(EntityNotFoundException.class)
    public String handleEntityNotFound(EntityNotFoundException ex) {
        log.error(ex.getMessage());
        return "error/404";
    }

    // 그 외 모든 예외 → 500 반환
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex) {
        log.error(ex.getMessage());
        return "error/500";
    }
}
