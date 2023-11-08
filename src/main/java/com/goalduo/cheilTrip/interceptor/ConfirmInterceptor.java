package com.goalduo.cheilTrip.interceptor;

import com.goalduo.cheilTrip.member.dto.MemberDto;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class ConfirmInterceptor implements HandlerInterceptor { 

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		MemberDto member = (MemberDto) session.getAttribute("userinfo");
		System.out.println("[인터셉터 요청]");
		if(member == null) {
			return false;
		}
		return true;
	}
	
}