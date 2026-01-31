package com.beyond.basic.board.author.controller;

import com.beyond.basic.board.author.domain.Author;
import com.beyond.basic.board.author.dtos.*;
import com.beyond.basic.board.author.service.AuthorService;
import com.beyond.basic.board.common.auth.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {
    private final AuthorService authorService;
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    public AuthorController(AuthorService authorService, JwtTokenProvider jwtTokenProvider) {
        this.authorService = authorService;
        this.jwtTokenProvider = jwtTokenProvider;
    }
//	1. 회원가입 : name, email, password를 받아 이메일이 중복인지 확인 후, 저장
//	(이메일 중복시 에러)
//            (password길이가 8글자이상, 이름이 없거나 이메일이 없을 시 에러)
//    url : /author/create
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid AuthorCreateDto dto, @RequestParam MultipartFile profileImage){
        System.out.println(dto);
        System.out.println(profileImage.getOriginalFilename());
        authorService.create(dto, profileImage);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 완료");
    }
//	2. 회원목록조회 : 모든 회원의 id, name, email의 리스트를 돌려줍니다.
//     url : /author/list
    @GetMapping("/list")
//    PreAuthorize : Authentication객체안의 권한정보를 확인하는 어노테이션
//    2개이상의 Role을 허용하는 경우 : "hasRole('ADMIN') or hasRole('SELLER')"
    @PreAuthorize("hasRole('ADMIN')")
    public List<AuthorListDto> findAll(){
        List<AuthorListDto> dtoList = authorService.findAll();
        authorService.findAll();
        return dtoList;
    }
//	2-2. 회원상세조회 : id를 받아 그 회원의 id, name, email, postCount, password, role을 돌려줍니다.
//            (없는 회원id 조회시 에러)
//    url : /author/{id}
    @GetMapping("/{id}")
    public AuthorDetailDto findById(@PathVariable Long id){
        AuthorDetailDto dto = authorService.findById(id);
        return dto;
    }
//   3. 회원삭제(hard delete) : id를 받아 그 회원을 삭제합니다. 질문1
//            (없는 회원id 조회시 에러)
//    url : /author/delete/{id}
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        authorService.delete(id);
        return "삭제 완료.";
    }
//   4. 비밀번호 업데이트 : email, password를 받아 해당 email의 회원의 비밀번호를 변경합니다.
//            (없는 회원email 조회시 에러)
//    url : /author/update/password
    @PatchMapping("/update/password")
    public String updatePw(@RequestBody AuthorUpdatePwDto dto){
        authorService.updatePw(dto);
        return "비밀번호 변경완료.";
    }
    @PostMapping("/login")
    public String login(@RequestBody AuthorLoginDto dto){
        Author author = authorService.login(dto);
        String token = jwtTokenProvider.createToken(author);
        return token;
    }
}
