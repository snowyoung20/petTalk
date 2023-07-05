package com.example.pettalk.service;


import com.example.pettalk.dto.PostRequestDto;
import com.example.pettalk.dto.PostResponseDto;
import com.example.pettalk.dto.StatusResult;
import com.example.pettalk.entity.Post;
import com.example.pettalk.entity.User;
import com.example.pettalk.jwt.JwtUtil;
import com.example.pettalk.repository.PostRepository;
import com.example.pettalk.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

//    public PostService(PostRepository postRepo
//   sitory) {
//        this.postRepository = postRepository;
//    }

    public List<PostResponseDto> getPost() {
        return postRepository.findAllByOrderByCreatedAtDesc().stream().map(PostResponseDto::new).toList();
    }

    // 게시글 조회
    @Transactional
    public PostResponseDto getPost(Long id) {
        // 해당하는 게시글을 찾아서 post에 저장
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("id가 일치하지 않습니다.")
        );
        return new PostResponseDto(post);
    }

    // 게시글 작성
    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto, User user) {

        // 전달받은 requestDto를 post객체에 넣어줌
        Post post = new Post(requestDto, user);

        // DB에 저장
        postRepository.save(post);

        // ResponseDto 형식으로 변환후 리턴
        return new PostResponseDto(post);
    }

    // 게시글 수정
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, User user) {

        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 글이 존재하지 않습니다.")
        );


        // 게시글의 작성자가 유저와 일치하면 게시글 내용을 수정한다.
        if (post.getUser().getUsername().equals(user.getUsername())) {
            post.update(requestDto);
        } else {
            throw new IllegalArgumentException("작성자만 수정이 가능합니다.");
        }

        return new PostResponseDto(post);
    }

    // 게시글 삭제
    @Transactional
    public StatusResult deletePost(Long id, User user) {

        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 글이 존재하지 않습니다.")
        );


        // 게시글의 작성자가 유저와 일치하면 게시글을 삭제한다.
        if (post.getUser().getUsername().equals(user.getUsername())) {
            postRepository.delete(post);
        } else {
            throw new IllegalArgumentException("작성자만 삭제가 가능합니다.");
            //return new StatusResult("작성자만 삭제할 수 있습니다.",400);
        }

        return new StatusResult("삭제 성공",200);
    }

}
