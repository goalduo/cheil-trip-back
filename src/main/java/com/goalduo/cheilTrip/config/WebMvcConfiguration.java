package com.goalduo.cheilTrip.config;

import com.goalduo.cheilTrip.interceptor.ConfirmInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
	
	private final Logger logger = LoggerFactory.getLogger(WebMvcConfiguration.class);

	private final List<String> patterns = Arrays.asList("/board/write");

	@Autowired
	private ConfirmInterceptor confirmInterceptor;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOriginPatterns("*")
				.allowedMethods("*")
				.allowCredentials(true)
				.maxAge(1800); // 1800초 동안 preflight 결과를 캐시에 저장
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(confirmInterceptor).addPathPatterns(patterns);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
	}

}
