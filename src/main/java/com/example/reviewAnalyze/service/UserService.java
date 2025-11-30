package com.example.reviewAnalyze.service;

import com.example.reviewAnalyze.entity.User;
import com.example.reviewAnalyze.dto.userDto.LoginDto;
import com.example.reviewAnalyze.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(LoginDto loginDto) {
        log.info("username={}, password={}", loginDto.getUsername(), loginDto.getPassword());
        if (userRepository.existsByUsername(loginDto.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        String password = passwordEncoder.encode(loginDto.getPassword());

        User user = User.builder()
                .username(loginDto.getUsername())
                .password(password)
                .role("USER")
                .build();

        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }
}
