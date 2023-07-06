package com.example.pettalk.entity;

import com.example.pettalk.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "great", nullable = false)
    private int greatCount=0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 게시글이 삭제되면 해당 게시글의 댓글 삭제
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    // 게시글이 삭제되면 해당 게시글의 좋아요 삭제
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Great> greats = new ArrayList<>();


    public Post(PostRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.user = user;
    }

    public void update(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public void increseGreatCount(){
        this.greatCount++;
    }

    public void decreseGreatCount(){
        this.greatCount--;
    }

}