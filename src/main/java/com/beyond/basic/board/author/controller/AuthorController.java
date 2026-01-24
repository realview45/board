package com.beyond.basic.board.author.controller;

import com.beyond.basic.board.author.dtos.AuthorCreateDto;
import com.beyond.basic.board.author.dtos.AuthorDetailDto;
import com.beyond.basic.board.author.dtos.AuthorListDto;
import com.beyond.basic.board.author.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {
    private final AuthorService authorService;
    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }
//	1. 회원가입 : name, email, password를 받아 이메일이 중복인지 확인 후, 저장
//	(이메일 중복시 에러)
//            (password길이가 8글자이상, 이름이 없거나 이메일이 없을 시 에러)
//    url : /author/create
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid AuthorCreateDto dto){
        authorService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 완료");
    }
//	2. 회원목록조회 : 모든 회원의 id, name, email의 리스트를 돌려줍니다.
//     url : /author/list
    @GetMapping("/list")
    public List<AuthorListDto> findAll(){
        List<AuthorListDto> dtoList = authorService.findAll();
        authorService.findAll();
        return dtoList;
    }
//	3. 회원상세조회 : id를 받아 그 회원의 id, name, email, postCount, password, role을 돌려줍니다.
//            (없는 회원id 조회시 에러)
//    url : /author/{id}
    @GetMapping("/{id}")
    public AuthorDetailDto findById(@PathVariable Long id){
        AuthorDetailDto dto = authorService.findById(id);
        return dto;
    }
}
