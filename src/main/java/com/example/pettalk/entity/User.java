package com.example.pettalk.entity;

import com.example.pettalk.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private String description;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(UserRequestDto.SignupRequestDto requestDto) {
        this.userId = requestDto.getUserId();
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.description = requestDto.getDescription();
    }
}