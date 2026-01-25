package com.beyond.basic.board.common.auth;

import com.beyond.basic.board.author.domain.Author;
import org.springframework.stereotype.Component;

//토큰 관련한 기능만수행메서드를 따로 넣고 싶을 때 서비스에서 부를 싱글톤 객체 생성
@Component
public class JwtTokenProvider {
    public String createToken(Author author){
        return "ok";
    }
}
