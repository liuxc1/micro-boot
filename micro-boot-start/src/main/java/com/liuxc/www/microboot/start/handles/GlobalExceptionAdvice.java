package com.liuxc.www.microboot.start.handles;

import org.eclipse.jetty.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理类
 */
@ControllerAdvice
public class GlobalExceptionAdvice {

    @ResponseBody // 本次的处理是基于Rest风格完成的
    @ExceptionHandler(Exception.class)
    public Object exceptionHandler(Exception exception){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("message", exception.getMessage()); // 直接获取异常信息
        map.put("status", HttpStatus.INTERNAL_SERVER_ERROR_500); // 设置一个HTTP状态码
        map.put("exception", exception.getClass().getName()); // 获取异常类型
        map.put("path", request.getRequestURI()); // 异常发生的路径
        return map; // 直接返回对象
    }
}
