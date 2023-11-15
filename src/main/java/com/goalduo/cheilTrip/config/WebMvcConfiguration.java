package com.goalduo.cheilTrip.config;

import com.goalduo.cheilTrip.filter.JwtAuthenticationFilter;
import com.goalduo.cheilTrip.interceptor.ConfirmInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

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

	@Bean
	public FilterRegistrationBean JwtAuthenticationFilter(JwtAuthenticationFilter jwtAuthenticationFilter){
		FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(jwtAuthenticationFilter);
		filterRegistrationBean.setOrder(1);
		filterRegistrationBean.addUrlPatterns("/user/test");
		return filterRegistrationBean;
	}


}
