package com.liuxc.www.microboot.start.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理类
 */
@RestController
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping("/error_404")
    public Object error_404() {
        return getObject("无法找到用户访问路径。");
    }

    @RequestMapping("/error_500")
    public Object error_500() {
        return getObject("服务器内部错误。");
    }

    private Object getObject(String content) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        HttpServletResponse response = requestAttributes.getResponse();
        Map<String, Object> errMap = new HashMap<String, Object>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String element = headerNames.nextElement();
            System.out.println(element + "===>" + request.getHeader(element));
        }
        assert response != null;
        errMap.put("status", response.getStatus()); // 响应编码
        errMap.put("content", content); // 适当性的带一点文字描述
        errMap.put("referer", request.getHeader("Referer")); // 获取之前的来源
        errMap.put("path", request.getRequestURI()); // 访问路径
        return errMap;
    }
}
