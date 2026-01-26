package com.beyond.basic.board.common.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//토큰 검증후 Authentication객체 생성
@Component
//GenericFilter를 내가 만든 JwtTokenFilter가 구현
public class JwtTokenFilter extends GenericFilter {
    @Value("${jwt.secretKey}")
    private String st_secret_key;
    @Override            //사용자의 http요청이 맨앞단에서 servletRequest로 변환되어 들어오고, 필터계층을 넣어준다.
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) servletRequest;
            String bearerToken = req.getHeader("Authorization");
            System.out.println(bearerToken);

            //토큰이 없다면 다음체인으로 넘어가기
            if (bearerToken == null) {
                filterChain.doFilter(servletRequest, servletResponse);
            }

            String token = bearerToken.substring(7);

            //서버키를 가지고 페이로드를 파싱을해서 재암호화 검증 및 claims추출
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(st_secret_key)
                    .build()
                    .parseClaimsJws(token).getBody();

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + claims.get("role")));


            //인증객체생성
            Authentication authentication = new UsernamePasswordAuthenticationToken(claims.getSubject(), token, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            //검증시에 에러가 난다면 token값이 다른것이므로 401에러(인증에러)
        }catch(Exception e){
            e.printStackTrace();
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
