package com.example.pettalk.service;


import com.example.pettalk.dto.UserRequestDto;
import com.example.pettalk.entity.User;
import com.example.pettalk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<Objects> signup(UserRequestDto.SignupRequestDto requestDto) {
        String userId = requestDto.getUserId();
        String password = passwordEncoder.encode(requestDto.getPassword());
        requestDto.setPassword(password);
        String userName = requestDto.getUsername();
        String description = requestDto.getDescription();

        Optional<User> checkUserId = userRepository.findByUserId(userId);
        if (checkUserId.isPresent()) {
            throw new IllegalArgumentException("중복된 아이디가 존재합니다.");
        }

        Optional<User> checkUserName = userRepository.findByUsername(userId);
        if (checkUserName.isPresent()) {
            throw new IllegalArgumentException("중복된 닉네임이 존재합니다.");
        }

        User user = new User(requestDto);
        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
