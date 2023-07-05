package com.example.pettalk.entity;

import com.example.pettalk.dto.CommentRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor

public class Comment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 댓글번호

    @Column(nullable = false)
    private String comment; // 댓글내용

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    @JsonIgnore
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonManagedReference
    private User user;

    public Comment(User user, CommentRequestDto commentRequestDto, Post post){
        this.user = user;
        this.post = post;
        this.comment = commentRequestDto.getComment();
    }

    public void update(CommentRequestDto commentRequestDto){
        this.comment = commentRequestDto.getComment();
    }

}