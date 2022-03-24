package com.liuxc.www.microboot.start.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log4j")
@Slf4j
public class Log4jController {
    @RequestMapping("/show")
    public Object show(String message) {
        log.trace("trace 常规日志输出-->{}",message);
        log.info("info 常规日志输出-->{}",message);
        log.debug("debug 常规日志输出-->{}",message);
        log.warn("WARN 常规日志输出-->{}",message);
        log.error("error 常规日志输出-->{}",message);
        return message;
    }
}
