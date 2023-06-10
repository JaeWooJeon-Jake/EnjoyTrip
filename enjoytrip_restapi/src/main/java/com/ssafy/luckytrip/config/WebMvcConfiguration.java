package com.ssafy.luckytrip.config;

import java.util.Arrays;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.luckytrip.interceptor.JwtInterceptor;

import io.swagger.models.HttpMethod;

@Configuration
@MapperScan(basePackages = {"com.ssafy.luckytrip.**.mapper"})
public class WebMvcConfiguration implements WebMvcConfigurer {
	
	private final List<String> patterns = Arrays.asList("/admin/**", "/board/write", "/board/update/*", "/board/delete/*", 
			 "/user/delete", "/user/userinfo","/user/emoji");
	
	private JwtInterceptor jwtInterceptor;

	public WebMvcConfiguration(JwtInterceptor jwtInterceptor) {
		super();
		this.jwtInterceptor = jwtInterceptor;
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		.allowedOriginPatterns("*")
		.allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name(), 
				HttpMethod.OPTIONS.name(), HttpMethod.PATCH.name(), HttpMethod.HEAD.name()).maxAge(1800);
	}
	
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor).addPathPatterns(patterns);
    }
	
}
