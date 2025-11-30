package com.example.reviewAnalyze.repository;

import com.example.reviewAnalyze.entity.User;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@Transactional
public class userRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveUser(){
        User testUser = User.builder()
                .username("aaa")
                .password("aaa")
                .build();

        userRepository.save(testUser);
        User savedUser = userRepository.findByUsername(testUser.getUsername())
                .orElse(null);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getUsername()).isEqualTo(testUser.getUsername());
    }
}
