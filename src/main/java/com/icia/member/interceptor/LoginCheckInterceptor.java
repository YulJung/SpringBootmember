package com.icia.member.interceptor;

import com.icia.member.common.SessionConst;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws IOException {
        String requestURI = request.getRequestURI();
        System.out.println("requestURI = "+requestURI);
        HttpSession session = request.getSession();
        if(session.getAttribute(SessionConst.LOGIN_EMAIL)==null){
            //미 로그인 상태
            //로그인을 하지 않는경우 바로 로그인 페이지로 보냄
            response.sendRedirect("/member/login?redirectURL="+ requestURI);
            return false;
        }else {
            return true;
        }

    }

}
