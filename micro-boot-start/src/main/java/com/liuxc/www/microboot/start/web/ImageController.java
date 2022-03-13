package com.liuxc.www.microboot.start.web;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * spring boot 处理图像数据
 */
@RestController
@RequestMapping("/image")
public class ImageController {

    @RequestMapping(value = "/getImage", produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public Object getImage() throws IOException {
        //读取资源文件
        ClassPathResource resource = new ClassPathResource("images/111.jpeg");
        return ImageIO.read(resource.getInputStream());
    }
}
