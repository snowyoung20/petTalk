package com.example.pettalk.controller;

import com.example.pettalk.dto.PostResponseDto;
import com.example.pettalk.dto.StatusResult;
import com.example.pettalk.dto.UserRequestDto;
import com.example.pettalk.jwt.JwtUtil;
import com.example.pettalk.security.UserDetailsImpl;
import com.example.pettalk.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
	private final UserService userService;
	private final JwtUtil jwtUtil;

	@PostMapping("/signup")
	public ResponseEntity<Objects> signUp(@RequestBody @Valid UserRequestDto.SignupRequestDto requestDto, BindingResult bindingResult) {

		// Validation 예외처리
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		if (fieldErrors.size() > 0) {
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
			}
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return userService.signup(requestDto);
	}

	@PostMapping("/login")
	public ResponseEntity<StatusResult> login(@RequestBody UserRequestDto.LoginRequestDto loginRequestDto, HttpServletResponse response) {
		try {
			userService.login(loginRequestDto);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(new StatusResult("회원을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST.value()));
		}

		response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(loginRequestDto.getUserId()));

		return ResponseEntity.ok().body(new StatusResult("로그인 성공", HttpStatus.CREATED.value()));
	}

	@PatchMapping("/update")
	public ResponseEntity<StatusResult> updateProfile(@AuthenticationPrincipal UserDetailsImpl userDetails,
	                                         @RequestBody UserRequestDto.updateRequestDto requestDto) {

		userService.update(userDetails.getUsername(), requestDto);
		return ResponseEntity.ok().body(new StatusResult("변경 성공", HttpStatus.CREATED.value()));
	}
}