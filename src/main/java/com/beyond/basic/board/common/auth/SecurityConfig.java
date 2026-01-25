package com.beyond.basic.board.common.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
//                a->AbstractHttpConfigurer(a)구리
//                csrf공격(일반적으로 쿠키를 활용한 (세션방식에서 활용파일구리) 공격)에 대한 방어 비활성화
                .csrf(AbstractHttpConfigurer::disable)
//                http basic은 email/pw를 인코딩하여 인증(전송)하는 간단한 인증방식. 비활성화.
                .httpBasic(AbstractHttpConfigurer::disable)
//                세션로그인방식 비활성화
                .sessionManagement(a->a.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                지정한 특정url을 제외한 모든 요청에 대해서 authenticated(인증처리)하겠다라는 의미
                .authorizeHttpRequests(a->a.requestMatchers(
                        "/author/create", "/author/login").permitAll().anyRequest().authenticated())
                .build();
    }
}
