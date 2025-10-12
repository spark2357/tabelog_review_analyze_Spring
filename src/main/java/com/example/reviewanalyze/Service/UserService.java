package com.example.reviewanalyze.Service;

import com.example.reviewanalyze.Domain.User;
import com.example.reviewanalyze.Dto.UserDto;
import com.example.reviewanalyze.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(UserDto userDto) {

        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        String password = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(password);
        user.setRole("ROLE_USER");

        System.out.println(user);

        userRepository.save(user);
        Optional<User> savedUser = userRepository.findByUsername(user.getUsername());
        System.out.println(savedUser);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }
}
