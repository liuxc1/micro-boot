package com.liuxc.www.microboot.start.config;

import com.liuxc.www.microboot.start.filters.MyFilter2;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置过滤器
 */
@Configuration
public class WebFilterConfig {
    @Bean
    public FilterRegistrationBean<MyFilter2> registrationMyFilter2() {
        FilterRegistrationBean<MyFilter2> registrationBean = new FilterRegistrationBean<MyFilter2>();
        registrationBean.setFilter(this.getMyFilter2());//设置自定义的filter
        registrationBean.addUrlPatterns("/*");//添加需要拦截的路径
        registrationBean.setOrder(100);//设置顺序
        return registrationBean;
    }

    @Bean
    public MyFilter2 getMyFilter2() {
        return new MyFilter2();
    }
}
