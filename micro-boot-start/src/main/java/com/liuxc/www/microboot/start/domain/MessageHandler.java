package com.liuxc.www.microboot.start.domain;

import lombok.Cleanup;
import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.io.InputStream;

public class MessageHandler {
    @SneakyThrows
    public void handler(User user){
        if(null == user){
            throw new Exception();
        }
    }

    @SneakyThrows

    public void red(){
       @Cleanup InputStream inputStream = new FileInputStream("D:ddd");
        byte[] bytes = new byte[1024];
        int read = inputStream.read(bytes);
        String s1 = new String(bytes, 0, read);
        System.out.println(s1);
    }
}
