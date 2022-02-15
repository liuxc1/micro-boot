package com.liuxc.www.microboot.start.banner;

import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

public class CustomBannerImpl implements Banner {

    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        out.println();
        out.println("www.liuxc.com");
        out.println();
        out.flush();
    }
}
