package com.liuxc.www.microboot.start;

import com.liuxc.www.microboot.start.banner.CustomBannerImpl;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@ImportResource({"classpath:conf/spring.xml"})
@ServletComponentScan
public class MicroBootStartApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        //创建应用实例
        SpringApplication springApplication = new SpringApplication(MicroBootStartApplication.class);
        //设置自定义启动banner
        springApplication.setBanner(new CustomBannerImpl());
        //设置banner输出模式
        springApplication.setBannerMode(Banner.Mode.CONSOLE);
        springApplication.run(args);

    }
}
