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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<PostListDto> findAll() {
        return postRepository.findAll().stream().filter(p->p.getDelYn().equals("N")).map(p->PostListDto.fromEntity(p)).collect(Collectors.toList());
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
