package com.example.pettalk.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "great")
public class Great {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public Great(User user, Post post){
        setUser(user);
        setPost(post);
    }

    public Great(User user, Comment comment){
        setUser(user);
        setComment(comment);
    }

    public void setUser(User user){
        this.user = user;
    }

    public void setPost(Post post){
        this.post = post;

        //현재 Like 객체가 post 객체의 likes 리스트에 포함되어 있는지 확인하고
        // 포함되어 있지 않을 경우 post 객체의 likes 리스트에 현재 Like 객체를 추가해줌
        // Like 객체와 Post 객체 사이에 관계설정
        if(!post.getGreats().contains(this))
            post.getGreats().add(this);
    }

    public void setComment(Comment comment){
        this.comment = comment;

        if(!comment.getGreats().contains(this))
            comment.getGreats().add(this);
    }


}
