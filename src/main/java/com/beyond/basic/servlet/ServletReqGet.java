package com.beyond.basic.servlet;

import com.beyond.basic.board.author.domain.Author;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

//서블릿(servlet)은 사용자의 http요청을 쉽게 처리하고, 사용자에게 http응답을 쉽게 조립해주는 기술
//서블릿에서는 url매핑을 메서드단위가 아닌, 클래스단위로 지정
@WebServlet("/servlet/get")
        //HttpServlet에는 doGet메서드가있음 오버라이딩
public class ServletReqGet extends HttpServlet {
    private final ObjectMapper objectMapper;

    public ServletReqGet(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    //url encoded방식으로 get요청보냄
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        사용자의 요청 : http://localhost:8080/servlet/get?id=1
//        사용자에게 줄 응답 : json객체형식
        String id = req.getParameter("id");
        System.out.println(id);
        Author author = Author.builder().email("hond1@gmail.com").build();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String jsonData = objectMapper.writeValueAsString(author);
        //body에 author를 json으로 만들어 resp에 넣을것임
        PrintWriter printWriter = resp.getWriter();
        printWriter.print(jsonData);
        printWriter.flush();
    }
}
