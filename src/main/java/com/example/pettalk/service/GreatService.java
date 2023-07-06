package com.example.pettalk.service;

import com.example.pettalk.dto.GreatResponseDto;
import com.example.pettalk.entity.Comment;
import com.example.pettalk.entity.Great;
import com.example.pettalk.entity.Post;
import com.example.pettalk.entity.User;
import com.example.pettalk.jwt.JwtUtil;
import com.example.pettalk.repository.CommentRepository;
import com.example.pettalk.repository.GreatRepository;
import com.example.pettalk.repository.PostRepository;
import com.example.pettalk.repository.UserRepository;
import com.example.pettalk.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GreatService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final GreatRepository greatRepository;
    private final JwtUtil jwtUtil;


    @Transactional
    public GreatResponseDto greatPost(Long id, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        // 해당 사용자가 게시글에 좋아요를 한적이 있는지 체크
        Optional<Great> checkUserAndPost = greatRepository.findByUserAndPost(user, post);

        // 좋아요를 했던 상태라면 좋아요를 취소한다.
        if(checkUserAndPost.isPresent()){
            greatRepository.delete(checkUserAndPost.get());
            post.decreseGreatCount();
            return new GreatResponseDto("좋아요가 취소되었습니다.");
        } else { // 좋아요를 한적이 없다면
            greatRepository.save(new Great(user, post));
            post.increseGreatCount();
            return new GreatResponseDto("해당 게시글을 좋아합니다.");
        }

    }


    @Transactional
    public GreatResponseDto greatComment(Long id, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );

        // 해당 사용자가 댓글에 좋아요를 한적이 있는지 체크
        Optional<Great> checkUserAndComment = greatRepository.findByUserAndComment(user, comment);

        // 좋아요를 했던 상태라면 좋아요를 취소한다.
        if(checkUserAndComment.isPresent()){
            greatRepository.delete(checkUserAndComment.get());
            comment.decreseGreatCount();
            return new GreatResponseDto("좋아요가 취소되었습니다.");
        } else { // 좋아요를 한적이 없다면
            greatRepository.save(new Great(user, comment));
            comment.increseGreatCount();
            return new GreatResponseDto("해당 댓글을 좋아합니다.");
        }

    }
}
