package com.beyond.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
//주로 web서블릿기반 구성요소(@WebServlet)을 스캔, 자동으로 빈으로 등록. url대상으로 라우팅작업과 비슷한 작업
@ServletComponentScan
public class BasicApplication {
	public static void main(String[] args) {
		SpringApplication.run(BasicApplication.class, args);
	}

}
