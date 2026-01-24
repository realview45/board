package com.beyond.basic.board.post.controller;

import com.beyond.basic.board.post.dtos.PostCreateDto;
import com.beyond.basic.board.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {
    private final PostService postService;
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }
    @PostMapping("/post/create")
    public ResponseEntity<?> create(@RequestBody PostCreateDto dto){
        postService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("게시글 작성완료.");
    }
}
