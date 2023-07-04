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

/*	@Transactional(readOnly = true)
	public String login(UserRequestDto.LoginRequestDto loginRequestDto, HttpServletResponse response) {
		String username = loginRequestDto.getUserId();
		String password = loginRequestDto.getPassword();

		User user = userRepository.findByUsername(username).orElseThrow(
				() -> new IllegalArgumentException("없는 사용자 입니다.")
		);

		if (!user.getPassword().equals(password)) {
			throw new IllegalArgumentException("비밀번호가 맞지 않습니다.");
		}
		response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername()));

		return "로그인 완료";
	}*/

//	@Transactional
//	public void update(String userId, UserRequestDto.updateRequestDto requestDto) {
//		Optional<User> updateUser = userRepository.findByUserId(userId);
//		User user = userRepository.findOne(userId);
//	}
}
