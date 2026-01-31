package com.beyond.basic.board.post.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PostScheduler {
    @Scheduled(fixedDelay = 1000)//1초마다 실행
    public void simpleScheduler(){
        log.info("=====스케줄러시작=====");

        log.info("=====스케줄러로직수행=====");

        log.info("=====스케줄러끝=====");
    }
}
