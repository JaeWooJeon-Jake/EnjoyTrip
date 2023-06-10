package com.ssafy.luckytrip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.ssafy.luckytrip"}) 안적어도 이 클래스 하위에 있는 컴포넌트는 자동으로 읽음
public class LuckytripApplication {

	public static void main(String[] args) {
		SpringApplication.run(LuckytripApplication.class, args);
	}

}
