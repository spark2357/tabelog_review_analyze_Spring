package com.example.reviewAnalyze.init;

import com.example.reviewAnalyze.dto.analyzeDto.AnalyzedResultDto;
import com.example.reviewAnalyze.entity.User;
import com.example.reviewAnalyze.repository.UserRepository;
import com.example.reviewAnalyze.service.ResultService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class TestDataInit implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ResultService resultService;
    
    private final ObjectMapper objectMapper;
    private final ResourceLoader resourceLoader;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // JSON 파일 읽기
        InputStream inputStream = resourceLoader.getResource("classpath:testData.json").getInputStream();
        AnalyzedResultDto testResult = objectMapper.readValue(inputStream, AnalyzedResultDto.class);
        User user = createTestUser("test");
        resultService.saveResult(user, testResult);
        User user2 = createTestUser("user");
        resultService.saveResult(user2, testResult);
    }

    private User createTestUser(String username) {
        String password = passwordEncoder.encode("test!");

        User user = User.builder()
                .username(username)
                .password(password)
                .role("ROLE_USER")
                .build();

        return userRepository.save(user);
    }
}
