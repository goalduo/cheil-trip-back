package com.goalduo.cheilTrip.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goalduo.cheilTrip.jwt.JwtProvider;
import com.goalduo.cheilTrip.util.ApiException;
import com.goalduo.cheilTrip.util.error.CommonErrorCode;
import com.goalduo.cheilTrip.util.error.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter implements Filter {

    private final JwtProvider jwtProvider;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String token = httpServletRequest.getHeader("Authorization");
        if(token == null) {
            httpServletResponse.sendError(400, "인증오류");
            return;
        }
        try{
            if(!jwtProvider.isValidToken(token)){
                httpServletResponse.sendError(400, "올바르지 않은 토큰");
                return;
            }
            chain.doFilter(request,response);
        } catch (Exception e){
            httpServletResponse.sendError(400, e.getMessage());
        }
    }
}
