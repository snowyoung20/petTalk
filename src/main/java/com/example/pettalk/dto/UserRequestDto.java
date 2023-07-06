package com.example.pettalk.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


public class UserRequestDto {
    @Setter
    @Getter
    public static class LoginRequestDto {
        private String userId;
        private String password;
    }
    @Getter
    @Setter
    public static class SignupRequestDto {
        // userId는 4자리 이상 10자리 이하의 영어 소문자와 숫자로 이루어져야함
        @Size(min = 4, max = 10)
        @Pattern(regexp = "^[a-z0-9]*$")
        @NotBlank
        private String userId;

        // password 는 8자리 이상 15자리 이하의 영어 대소문자와 숫자로 이루어져야함
        @Size(min = 8, max = 15)
        @Pattern(regexp = "^[a-zA-Z0-9]*$")
        @NotBlank
        private String password;

        private String username;
        private String description;
    }

    @Getter
    @Setter
    public static class updateRequestDto {
        private String password;

        @Pattern(regexp = "^[A-Za-z0-9]{8,15}$")
        private String newPassword;

        @Pattern(regexp = "^[A-Za-z0-9]{8,15}$")
        private String newPasswordCheck;

        private String description;
    }
}
