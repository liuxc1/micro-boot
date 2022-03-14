package com.liuxc.www.microboot.start.Interceptors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AOPInterceptor  {
    @Around("execution(* com.liuxc.www.microboot.start.service..*.*(..))")
    public Object aroundInterceptor(ProceedingJoinPoint point) throws Throwable {
        System.out.println("开始执行AOPInterceptor。。。");
        return point.proceed(point.getArgs());
    }
}
