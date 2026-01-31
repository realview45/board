package com.beyond.basic.board.post.service;

import com.beyond.basic.board.author.domain.Author;
import com.beyond.basic.board.author.repository.AuthorRepository;
import com.beyond.basic.board.post.domain.Post;
import com.beyond.basic.board.post.dtos.PostCreateDto;
import com.beyond.basic.board.post.dtos.PostDetailDto;
import com.beyond.basic.board.post.dtos.PostListDto;
import com.beyond.basic.board.post.dtos.PostSearchDto;
import com.beyond.basic.board.post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    public Page<PostListDto> findAll(Pageable pageable, PostSearchDto searchDto) {
//        return postRepository.findAll().stream().filter(p->p.getDelYn().equals("N")).map(p->PostListDto.fromEntity(p)).collect(Collectors.toList());
        Specification<Post> specification = new Specification<Post>() {//specification에 쿼리의 where뒤의 것들을 담아준다.
            @Override
            public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                //root : 엔티티의 컬럼명을 접근하기위한객체, criteriaBuilder:쿼리를 생성하기위한 객체
                if(searchDto.getTitle()!=null){
                    predicateList.add(criteriaBuilder.like(root.get("title"), "%"+searchDto.getTitle()+"%"));
                }
                if(searchDto.getCategory()!=null){
                    predicateList.add(criteriaBuilder.equal(root.get("category"),searchDto.getCategory()));
                }
                if(searchDto.getContents()!=null){
                    predicateList.add(criteriaBuilder.like(root.get("contents"),"%"+searchDto.getContents()+"%"));
                }
                Predicate[] predicateArr = new Predicate[predicateList.size()];
                for(int i=0;i<predicateArr.length;i++){
                    predicateArr[i]=predicateList.get(i);
                }
//                Predicate에는 검색조건들이 담길것이고, 이 Predicate list를 한줄의 predicate로 조립한다.
                Predicate predicate = criteriaBuilder.and(predicateArr);
                return predicate;
            }
        };
        return postRepository.findAll(specification, pageable).map(p->PostListDto.fromEntity(p));
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
