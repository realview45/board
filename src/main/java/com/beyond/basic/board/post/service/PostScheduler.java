//package com.beyond.basic.board.post.service;
//
//import com.beyond.basic.board.post.domain.Post;
//import com.beyond.basic.board.post.repository.PostRepository;
//import jakarta.transaction.Transactional;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Component
//@Slf4j
//@Transactional
//public class PostScheduler {
////    @Scheduled(fixedDelay = 1000)//1초마다 실행
////    public void simpleScheduler(){
////        log.info("=====스케줄러시작=====");
////
////        log.info("=====스케줄러로직수행=====");
////
////        log.info("=====스케줄러끝=====");
////    }
//
////    cron을 통해 작업수행 미세조정가능
////    cron의 각 자리는 "초 분 시간 일 월 요일"의 의미
////    0 0 * * * * : 매월 매일 매시간 0분 0초에 의미
//    private final PostRepository postRepository;
//    public PostScheduler(PostRepository postRepository) {
//        this.postRepository = postRepository;
//    }
//
//    @Scheduled(cron ="0 0/1 * * * *")//1분마다
//    public void postSchedule(){
//        log.info("=====스케줄러시작=====");
//        List<Post> postList=postRepository.findAllByAppointment("Y");
//        LocalDateTime now = LocalDateTime.now();
//        for(Post p : postList){
//            if(p.getAppointmentTime().isBefore(now)){
//                p.updateApointment("N");
//            }
//        }
////        post 전체중 Y인건을 조회후 , 그중 현재시간보다 이전인 데이터는 N로 변경 예약
//        log.info("=====스케줄러끝=====");
//    }
//}
