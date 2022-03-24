package com.liuxc.www.autostart.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(value = "com.liuxc.user")
public class User {
    private String userName;
    private String passWord;
    private Integer age;

}
