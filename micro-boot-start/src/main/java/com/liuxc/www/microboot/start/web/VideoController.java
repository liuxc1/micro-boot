package com.liuxc.www.microboot.start.web;

import com.liuxc.www.microboot.start.handles.VideoResponseHandle;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/video")
public class VideoController {
    private final VideoResponseHandle videoResponseHandle;

    public VideoController(VideoResponseHandle videoResponseHandle) {
        this.videoResponseHandle = videoResponseHandle;
    }
    @RequestMapping("/getVideo")
    public void getVideo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletWebRequest requestAttributes = (ServletWebRequest)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request1 = requestAttributes.getRequest();
        HttpServletResponse response1 = requestAttributes.getResponse();


        this.videoResponseHandle.handleRequest(request, response);
    }
}
