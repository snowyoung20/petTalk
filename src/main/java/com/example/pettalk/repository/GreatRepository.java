package com.example.pettalk.repository;

import com.example.pettalk.entity.Comment;
import com.example.pettalk.entity.Great;
import com.example.pettalk.entity.Post;
import com.example.pettalk.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GreatRepository extends JpaRepository<Great, Long> {
    Optional<Great> findByUserAndPost(User user, Post post);
    Optional<Great> findByUserAndComment(User user, Comment comment);
}
