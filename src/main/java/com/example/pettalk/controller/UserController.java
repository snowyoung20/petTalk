package com.example.pettalk.controller;

import com.example.pettalk.dto.UserRequestDto;
import com.example.pettalk.dto.UserResponseDto;
import com.example.pettalk.entity.User;
import com.example.pettalk.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ResponseBody
    @PostMapping("/login")
    public String login(@RequestBody UserRequestDto.LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }

//	@PatchMapping("/update/{userId}")
//	public ResponseEntity<Objects> updateUser(@RequestHeader("Authorization") String accessToken,
//	                                          @RequestBody UserRequestDto.updateRequestDto requestDto) {
//		userService.update(accessToken, requestDto);
//		return ApiResponse.createSuccessWithNoContent(ResponseCode.USER_MODIFY_PASSWORD_SUCCESS);
//	}
}