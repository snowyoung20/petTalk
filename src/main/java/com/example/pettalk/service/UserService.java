package com.example.pettalk.service;


import com.example.pettalk.dto.UserRequestDto;
import com.example.pettalk.entity.User;
import com.example.pettalk.jwt.JwtUtil;
import com.example.pettalk.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

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

	public void login(UserRequestDto.LoginRequestDto requestDto) {
		String userId = requestDto.getUserId();
		String password = requestDto.getPassword();

		User user = userRepository.findByUsername(userId).orElseThrow(
				() -> new IllegalArgumentException("등록된 사용자가 없습니다.")
		);

		if(!passwordEncoder.matches(password, user.getPassword())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}
	}

	@Transactional
	public void update(String username, UserRequestDto.updateRequestDto requestDto) {
		if (!requestDto.getNewPassword().equals(requestDto.getNewPasswordCheck())) {
			throw new IllegalArgumentException("변경할 비밀번호가 일치하지 않습니다.");
		}

		String password = requestDto.getPassword();
		String newPassword = passwordEncoder.encode(requestDto.getNewPassword());
		requestDto.setNewPassword(newPassword);
		String newDescription = requestDto.getDescription();

		User updateUser = userRepository.findByUsername(username).orElseThrow(
				() -> new IllegalArgumentException("등록된 사용자가 없습니다.")
		);

		if(!passwordEncoder.matches(password, updateUser.getPassword())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}

		updateUser.setPassword(newPassword);
		updateUser.setDescription(newDescription);

		userRepository.save(updateUser);
	}
}
