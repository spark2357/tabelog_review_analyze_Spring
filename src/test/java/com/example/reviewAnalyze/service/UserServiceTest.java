package com.example.reviewAnalyze.service;

import com.example.reviewAnalyze.dto.userDto.LoginDto;
import com.example.reviewAnalyze.entity.User;
import com.example.reviewAnalyze.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    @DisplayName("회원가입 성공")
    @Test
    void saveUser() {
        LoginDto loginDto = new LoginDto("aaa", "aaa");
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded");

        userService.register(loginDto);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(captor.capture());

        User savedUser = captor.getValue();
        assertThat(savedUser.getUsername()).isEqualTo("aaa");
        assertThat(savedUser.getPassword()).isEqualTo("encoded");
        assertThat(savedUser.getRole()).isEqualTo("USER");
    }

    @DisplayName("중복 아이디 실패")
    @Test
    void usernameExists() {
        LoginDto loginDto = new LoginDto("aaa", "aaa");
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        assertThatThrownBy(() -> userService.register(loginDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 아이디입니다.");
    }
}