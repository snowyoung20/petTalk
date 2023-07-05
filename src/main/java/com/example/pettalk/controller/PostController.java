package com.example.pettalk.controller;

import com.example.pettalk.dto.PostRequestDto;
import com.example.pettalk.dto.PostResponseDto;
import com.example.pettalk.dto.StatusResult;
import com.example.pettalk.security.UserDetailsImpl;
import com.example.pettalk.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pettalk")
public class PostController {

    private final PostService postService;

    private String test;

    @GetMapping("/")
    public List<PostResponseDto> getPost() {
        return postService.getPost();
    }

    // 게시글 조회
    @GetMapping("/{id}")
    public PostResponseDto getPost(@PathVariable Long id){
        return postService.getPost(id);
    }

    // 게시글 작성
//    @PostMapping("/create")
//    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
//        return postService.createPost(requestDto, userDetails.getUser());
//    }

	@PostMapping("/create")
	public ResponseEntity<PostResponseDto> createPost(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PostRequestDto requestDto) {
		PostResponseDto result = postService.createPost(requestDto, userDetails.getUser());

		return ResponseEntity.status(201).body(result);
	}

    // 게시글 수정
    @PutMapping("/{id}/update")
    public PostResponseDto updatePost(@PathVariable Long id,
                                      @RequestBody PostRequestDto requestDto,
                                      HttpServletRequest request){
        return postService.updatePost(id, requestDto, request);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}/delete")
    public StatusResult deletePost(@PathVariable Long id, HttpServletRequest request){
        return postService.deletePost(id, request);
    }
}
