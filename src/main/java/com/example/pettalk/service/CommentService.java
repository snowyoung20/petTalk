package com.example.pettalk.service;

import com.example.pettalk.dto.CommentRequestDto;
import com.example.pettalk.dto.CommentResponseDto;
import com.example.pettalk.dto.StatusResult;
import com.example.pettalk.entity.Comment;
import com.example.pettalk.entity.Post;
import com.example.pettalk.entity.User;
import com.example.pettalk.jwt.JwtUtil;
import com.example.pettalk.repository.CommentRepository;
import com.example.pettalk.repository.PostRepository;
import com.example.pettalk.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;



    // 댓글 작성 API
    @Transactional
    public CommentResponseDto createComment(Long postID,
                                            CommentRequestDto commentRequestDto,
                                            HttpServletRequest httpServletRequest) {
        User user = checkToken(httpServletRequest);

        // 게시글 유무 판단
        Post post = postRepository.findById(postID).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );


        Comment comment = new Comment(user, commentRequestDto, post);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }


    // 댓글 수정 API
    @Transactional
    public CommentResponseDto updateComment(Long commentId,
                                            CommentRequestDto commentRequestDto,
                                            HttpServletRequest httpServletRequest) {
        User user = checkToken(httpServletRequest);

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );

        // 댓글의 작성자와 들어온값의 작성자 비교. 일치하면 업데이트
        if(comment.getUser().getUsername().equals(user.getUsername())){
            comment.update(commentRequestDto);
            return new CommentResponseDto(comment);
        } else {
            throw new IllegalArgumentException("작성자만 수정이 가능합니다.");
        }
    }


    // 댓글 삭제 API
    public StatusResult deleteComment(Long commentId,
                                            HttpServletRequest httpServletRequest) {
        User user = checkToken(httpServletRequest);

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );

        // 댓글의 작성자와 들어온값의 작성자 비교. 일치하면 댓글삭제
        if(comment.getUser().getUsername().equals(user.getUsername())){
            commentRepository.delete(comment);
            return new StatusResult("삭제가 성공되었습니다", 200);
        } else {
            return new StatusResult("작성자만 삭제가 가능합니다.", 400);
        }
    }


    // 토큰검증
    public User checkToken(HttpServletRequest request){

        //클라이언트의 요청에서 JWT 토큰 획득
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 추출된 토큰이 NULL인지 확인 후 토큰 유효성 검사 실행
        if(token != null){

            // 유효성 검사 시작
            if(jwtUtil.validateToken(token)){
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                // 토큰이 존재하지 않으면 Token Error라는 메시지를 출력하며 예외를 발생 시킴
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            return user;
        }
        return null;

    }


}
