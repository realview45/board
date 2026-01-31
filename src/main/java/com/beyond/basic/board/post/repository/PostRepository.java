package com.beyond.basic.board.post.repository;

import com.beyond.basic.board.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    Optional<Post> findByIdAndDelYn(Long id, String delYn);
    //검색 + 페이징처리까지 할경우, 아래와 같이 매개변수 선언. (Specification, Pageable 순서 - SimpleJpaRepository에서 정의)
    Page<Post> findAll(Specification<Post> specification, Pageable pageable);
}
