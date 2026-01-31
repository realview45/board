package com.beyond.basic.board.common.init;

import com.beyond.basic.board.author.domain.Author;
import com.beyond.basic.board.author.domain.Role;
import com.beyond.basic.board.author.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Transactional
//CommandLineRunner를 구현함으로서 아래 run메서드가 스프링빈으로 등록되는 시점에 자동실행
public class InitialDataLoad implements CommandLineRunner {
    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;

    public InitialDataLoad(AuthorRepository authorRepository, PasswordEncoder passwordEncoder) {
        this.authorRepository = authorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if(authorRepository.findByEmail("admin@naver.com").isPresent()){
            return;
        }
        authorRepository.save(Author.builder()
                .name("admin").email("admin@naver.com").role(Role.ADMIN).password(passwordEncoder.encode("12341234")).build());
    }
}
