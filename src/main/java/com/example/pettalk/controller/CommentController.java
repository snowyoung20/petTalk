package com.example.pettalk.controller;

import com.example.pettalk.dto.CommentRequestDto;
import com.example.pettalk.dto.CommentResponseDto;
import com.example.pettalk.dto.StatusResult;
import com.example.pettalk.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pettalk")
public class CommentController {

    private final CommentService commentService;


    // 댓글 작성 API
    @PostMapping("/comment/{postID}")

    // 게시글 번호, 댓글내용, 유저정보 가져오기
    public CommentResponseDto createComment(@PathVariable Long postID,
                                            @RequestBody CommentRequestDto commentRequestDto,
                                            HttpServletRequest httpServletRequest){

        return commentService.createComment(postID, commentRequestDto, httpServletRequest);
    }



    // 댓글 수정 API
    @PutMapping("/comment/{commentId}")

    // 댓글번호, 수정할 댓글내용, 유저정보 가져오기
    public CommentResponseDto updateComment(@PathVariable Long commentId,
                                            @RequestBody CommentRequestDto commentRequestDto,
                                            HttpServletRequest httpServletRequest){

        return commentService.updateComment(commentId, commentRequestDto, httpServletRequest);
    }



    // 댓글 삭제 API
    @DeleteMapping("/comment/{commentId}")

    // 댓글번호, 유저정보 가져오기
    public StatusResult deleteComment(@PathVariable Long commentId,
                                      HttpServletRequest httpServletRequest){

        return commentService.deleteComment(commentId, httpServletRequest);
    }





}
