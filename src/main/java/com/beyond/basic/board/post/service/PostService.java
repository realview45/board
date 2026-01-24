package com.beyond.basic.board.post.service;

import com.beyond.basic.board.author.domain.Author;
import com.beyond.basic.board.author.repository.AuthorRepository;
import com.beyond.basic.board.post.dtos.PostCreateDto;
import com.beyond.basic.board.post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional//org.spring으로 import!
public class PostService {
    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;
    @Autowired
    public PostService(PostRepository postRepository, AuthorRepository authorRepository) {
        this.postRepository = postRepository;
        this.authorRepository = authorRepository;
    }
    public void create(PostCreateDto dto) {
        //로그인 이후 아래줄은 날릴예정
        Author author = authorRepository.findByEmail(dto.getAuthorEmail()).orElseThrow(()->new EntityNotFoundException("글쓴이가 존재하지 않습니다."));
        postRepository.save(dto.toEntity(author));
    }
}
