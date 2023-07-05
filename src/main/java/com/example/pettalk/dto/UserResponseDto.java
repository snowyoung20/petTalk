package com.example.pettalk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private String userId;
    private String username;
    private String description;
}
