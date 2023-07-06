package com.example.pettalk.controller;


import com.example.pettalk.dto.GreatResponseDto;
import com.example.pettalk.security.UserDetailsImpl;
import com.example.pettalk.service.GreatService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pettalk")
public class GreatController {
    private final GreatService greatService;

    // 게시글 좋아요 기능
    // 게시글 id와 유저정보를 받아온다.
    @PostMapping("/great/post/{id}")
    public GreatResponseDto greatPost(@PathVariable Long id,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails){
        return greatService.greatPost(id, userDetails);
    }

    // 댓글 좋아요 기능
    // 댓글 id와 유저정보를 받아온다.
    @PostMapping("/great/comment/{id}")
    public GreatResponseDto greatPostComment(@PathVariable Long id,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails){
        return greatService.greatComment(id, userDetails);
    }


}
