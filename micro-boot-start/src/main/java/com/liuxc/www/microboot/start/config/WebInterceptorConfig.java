package com.liuxc.www.microboot.start.config;

import com.liuxc.www.microboot.start.Interceptors.MyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebInterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getMyInterceptor()).addPathPatterns("/**").order(1);
    }
    @Bean
    public MyInterceptor getMyInterceptor(){
        return new MyInterceptor();
    }
}
