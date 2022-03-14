package com.liuxc.www.microboot.start.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
//指定配置文件的路径
@PropertySource(value = "classpath:dept.properties", encoding = "UTF-8")
@ConfigurationProperties(prefix = "dept")
@Component
public class Dept {
    private Integer did;
    private String dname;
    private Company company;
    private List<Employee> employees;
}

