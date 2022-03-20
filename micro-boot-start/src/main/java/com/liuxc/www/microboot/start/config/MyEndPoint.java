package com.liuxc.www.microboot.start.config;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Endpoint(id = "myEndPoint")
public class MyEndPoint {
    @ReadOperation
    public Map<String, Object> getMyEndPoint() {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("message", "hello world");
        return resultMap;
    }
}
