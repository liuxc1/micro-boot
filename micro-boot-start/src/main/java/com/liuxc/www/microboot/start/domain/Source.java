package com.liuxc.www.microboot.start.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "source")
@Data
public class Source {
    //使用自动bean注入
    private String mysql;
    private String redis;
    private Map<String, Object> info;
    private List<String> messages;
}
