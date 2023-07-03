package com.example.pettalk.config;

import com.example.pettalk.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {
	private final UserDetailService userService;

}
