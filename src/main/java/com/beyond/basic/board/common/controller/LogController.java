package com.beyond.basic.board.common.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {
    //logback 객체를 만드는 방법1. logger라는 변수를 만들고, 로그입력 자주사용X
    private static Logger logger = LoggerFactory.getLogger(LogController.class);
    @GetMapping("/log/test")
    public String logTest(){
//        system println의 문제점 1)출력의 성능이 떨어짐 2)로그분류작업 불가
//        가장 많이 사용디는 로그라이브러시 : logback
//        System.out.println("hello world");
        logger.trace("trace로그 입니다.");
        logger.debug("debug로그 입니다.");//test시에 로그를 출력하고 싶을 때 사용
        logger.info("info로그 입니다.");//운영서버는 수없이 많은 로그가 찍히므로 간단한 로그만 출력
        logger.error("error로그 입니다.");
        return "OK";
    }
}
