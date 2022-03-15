package com.liuxc.www.microboot.start.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * 注册错误页面
 */
@Configuration
public class ErrorConfig implements ErrorPageRegistrar {
    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        registry.addErrorPages(
                new ErrorPage(HttpStatus.NOT_FOUND, "/error/error_404"),// 已经添加了新的错误页
                new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/error_500") // // 已经添加了新的错误页
        );
    }
}
