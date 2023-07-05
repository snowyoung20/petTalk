package com.example.pettalk.controller;

import com.example.pettalk.dto.CommentRequestDto;
import com.example.pettalk.dto.CommentResponseDto;
import com.example.pettalk.dto.StatusResult;
import com.example.pettalk.security.UserDetailsImpl;
import com.example.pettalk.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
                                            @AuthenticationPrincipal UserDetailsImpl userDetails){

        return commentService.createComment(postID, commentRequestDto, userDetails.getUser());
    }



    // 댓글 수정 API
    @PutMapping("/comment/{commentId}")

    // 댓글번호, 수정할 댓글내용, 유저정보 가져오기
    public CommentResponseDto updateComment(@PathVariable Long commentId,
                                            @RequestBody CommentRequestDto commentRequestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails){

        return commentService.updateComment(commentId, commentRequestDto, userDetails.getUser());
    }



    // 댓글 삭제 API
    @DeleteMapping("/comment/{commentId}")

    // 댓글번호, 유저정보 가져오기
    public StatusResult deleteComment(@PathVariable Long commentId,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails){

        return commentService.deleteComment(commentId, userDetails.getUser());
    }





}