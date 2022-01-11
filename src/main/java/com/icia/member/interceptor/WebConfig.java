package com.icia.member.interceptor;


import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new LoginCheckInterceptor()) //인터셋터 클래스 내용을 넘김
                .order(1)// 적용되는 순서
                .addPathPatterns("/**") //모든 주소에대해 인터셉터를 적용
                .excludePathPatterns("/","/member/save","/member/login","/css/**");
    }

}
