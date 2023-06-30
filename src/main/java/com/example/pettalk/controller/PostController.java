package com.example.pettalk.controller;

import com.example.pettalk.dto.PostResponseDto;
import com.example.pettalk.service.PostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pettalk")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    @GetMapping("/")
    public List<PostResponseDto> getPost() {
        return postService.getPost();
    }
}
