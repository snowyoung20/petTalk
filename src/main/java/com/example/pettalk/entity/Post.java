package com.example.pettalk.entity;

import com.example.pettalk.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "post")
@NoArgsConstructor
public class Post extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "contents", nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

//    public Post(PostRequestDto requestDto) {
//        this.title = requestDto.getTitle();
//        this.contents = requestDto.getContents();
//    }
//
//    public void update(PostRequestDto requestDto) {
//        this.title = requestDto.getTitle();
//        this.contents = requestDto.getContents();
//    }
//
//    public void addUser (User user) {
//        this.user = user;
//    }
}
