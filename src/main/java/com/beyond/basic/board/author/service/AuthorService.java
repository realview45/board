package com.beyond.basic.board.author.service;

import com.beyond.basic.board.author.domain.Author;
import com.beyond.basic.board.author.dtos.*;
import com.beyond.basic.board.author.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional//org.spring으로 import!//트랜잭션의 단위는 Service의 메서드가 된다.
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AuthorService(AuthorRepository authorRepository, PasswordEncoder passwordEncoder) {
        this.authorRepository = authorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void create(AuthorCreateDto dto) {
        if(authorRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new IllegalArgumentException("이메일이 중복입니다.");
        }
        authorRepository.save(dto.toEntity(passwordEncoder.encode(dto.getPassword())));
    }

    public List<AuthorListDto> findAll() {
        return authorRepository.findAll().stream().map(a->AuthorListDto.fromEntity(a)).collect(Collectors.toList());
    }

    public AuthorDetailDto findById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("엔티티(Author)가 없습니다."));
        return AuthorDetailDto.fromEntity(author);
    }

    public void delete(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("엔티티(Author)가 이미 없습니다."));
        authorRepository.delete(author);
    }

    public void updatePw(AuthorUpdatePwDto dto) {
        //로그인 상태에서는 아래줄이 필요없음
        Author author = authorRepository.findByEmail(dto.getEmail()).orElseThrow(()->new EntityNotFoundException("엔티티(Author)가 없습니다."));
        //dirtychecking으로 save필요없이 변경만 해주면된다.
        author.updatePw(dto.getPassword());
    }

    public Author login(AuthorLoginDto dto) {
        boolean loginSuccess = true;
        Optional<Author> author = authorRepository.findByEmail(dto.getEmail());
        if(!author.isPresent()||!passwordEncoder.matches(dto.getPassword(),author.get().getPassword())){
            loginSuccess=false;
        }
        if(!loginSuccess){
            throw new IllegalArgumentException("이메일 또는 비밀번호가 다릅니다.");
        }
        return author.get();
    }
}
