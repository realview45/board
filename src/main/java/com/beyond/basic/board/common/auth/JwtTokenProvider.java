package com.beyond.basic.board.common.auth;

import com.beyond.basic.board.author.domain.Author;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

//토큰 관련한 기능만수행메서드를 따로 넣고 싶을 때 서비스에서 부를 싱글톤 객체 생성
@Component//순서 2또는 3
public class JwtTokenProvider {
//    중요정보의 경우 application.yml 저장. Value를 통해 주입.
    @Value("${jwt.secretKey}")//순서 1이어야만 함
    private String st_secret_key;
    @Value("${jwt.expiration}")//순서 1이어야만 함
    private int expiration;
    //순서 2또는 3 실행순서 보장되야함
    private Key secret_key;
    @PostConstruct //생성자 다음에 실행하겠다 객체가 만들어진 다음에 실행 적어도 @Value주입 이후에 실행이 보장된다.
    public void init(){
        secret_key = new SecretKeySpec(Base64.getDecoder().decode(st_secret_key),
                SignatureAlgorithm.HS512.getJcaName());//st_secret_key를 Base64로 디코딩 + HS(SHA)512로 암호화한 값
    }
    public String createToken(Author author){
        //        sub : abc@naver.com 형태
        Claims claims = Jwts.claims().setSubject(author.getEmail());
//        주된 키값을 제외한 나머지 정보는 put을 사용하여 key:value세팅
        claims.put("role", author.getRole().toString());
//        ex)claims.put("age", author.getAge()); 형태가능

        Date now = new Date();

//        토큰의 구성요소 : 헤더, 페이로드, 시그니처(서명부)
//        json,signature를 인코딩구리
        String token = Jwts.builder()
//                아래 3가지 요소는 페이로드
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+expiration*60*1000L))//30분:30*60*1000밀리초 : 밀리초형태로 변환
//              secret키를 통해 서명값(signature) 생성
                .signWith(secret_key)
                .compact();
        return token;
    }
}
