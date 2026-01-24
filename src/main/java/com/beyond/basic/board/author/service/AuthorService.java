package com.beyond.basic.board.author.service;

import com.beyond.basic.board.author.domain.Author;
import com.beyond.basic.board.author.dtos.AuthorCreateDto;
import com.beyond.basic.board.author.dtos.AuthorDetailDto;
import com.beyond.basic.board.author.dtos.AuthorListDto;
import com.beyond.basic.board.author.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional//트랜잭션의 단위는 Service의 메서드가 된다.
public class AuthorService {
    private final AuthorRepository authorRepository;
    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void create(@Valid AuthorCreateDto dto) {
        authorRepository.save(dto.toEntity());
    }

    public List<AuthorListDto> findAll() {
        return authorRepository.findAll().stream().map(a->AuthorListDto.fromEntity(a)).collect(Collectors.toList());
    }

    public AuthorDetailDto findById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("엔티티(Author)가 없습니다."));
        return AuthorDetailDto.fromEntity(author);
    }
}
