package com.beyond.basic.board.post.service;

import com.beyond.basic.board.author.domain.Author;
import com.beyond.basic.board.author.repository.AuthorRepository;
import com.beyond.basic.board.post.domain.Post;
import com.beyond.basic.board.post.dtos.PostCreateDto;
import com.beyond.basic.board.post.dtos.PostDetailDto;
import com.beyond.basic.board.post.dtos.PostListDto;
import com.beyond.basic.board.post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
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
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Author author = authorRepository.findByEmail(email).orElseThrow(()->new EntityNotFoundException("글쓴이가 존재하지 않습니다."));
        postRepository.save(dto.toEntity(author));
    }

    public Page<PostListDto> findAll(Pageable pageable) {
//        return postRepository.findAll().stream().filter(p->p.getDelYn().equals("N")).map(p->PostListDto.fromEntity(p)).collect(Collectors.toList());
        return postRepository.findAll(pageable).map(p->PostListDto.fromEntity(p));
    }

    public PostDetailDto findById(Long id) {
        Post post = postRepository.findByIdAndDelYn(id,"N").orElseThrow(()->new EntityNotFoundException("엔티티(Post)가 없습니다."));
        return PostDetailDto.fromEntity(post);
    }

    public void delete(Long id) {
        Post post = postRepository.findByIdAndDelYn(id,"N").orElseThrow(()->new EntityNotFoundException("엔티티(Post)가 없습니다."));
        post.delete();
    }
}
