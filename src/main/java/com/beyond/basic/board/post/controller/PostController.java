package com.beyond.basic.board.post.controller;

import com.beyond.basic.board.post.dtos.PostCreateDto;
import com.beyond.basic.board.post.dtos.PostDetailDto;
import com.beyond.basic.board.post.dtos.PostListDto;
import com.beyond.basic.board.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {
    private final PostService postService;
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }
//    	2-3. 게시글등록 : title, contents, category, authorEmail을 받아 게시글을 등록합니다.
//            (없는 authorEmail일시 에러)
//            (title, contents가 없을시 에러)
//    url : /post/create
    @PostMapping("/post/create")
    public ResponseEntity<?> create(@RequestBody PostCreateDto dto){
        postService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("게시글 작성완료.");
    }
//    	5-1. 게시글목록조회 : 각 게시글의 id, title, category, authorEmail을 돌려줍니다.
//    url : /posts
    @GetMapping("/posts")
    public Page<PostListDto> findAll(Pageable pageable){
        Page<PostListDto> dtoList =postService.findAll(pageable);
        return dtoList;
    }
//    	5-2. 게시글조회 : id를 받아 그 게시글의 id, title, contents, category, authorEmail을 돌려줍니다.
//            (없는 게시글id 조회시 에러)
//    url : /post/{id}
    @GetMapping("/post/{id}")
    public PostDetailDto findById(@PathVariable Long id){
        return postService.findById(id);
    }
//    	5-3. 게시글삭제(soft delete) : id를 받아 게시물을 softdelete합니다
//            (없는 게시글id 조회시 에러)
//    url : /post/{id}
    @PatchMapping("/post/{id}")//delYn값을 "Y"로 업데이트
    public String delete(@PathVariable Long id){
        postService.delete(id);
        return "게시글 삭제완료.";
    }

}
