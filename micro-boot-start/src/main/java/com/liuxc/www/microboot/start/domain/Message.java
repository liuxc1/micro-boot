package com.liuxc.www.microboot.start.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private String title;
    private String content;
    @JSONField(format = "yyyy年MM月dd日") // FastJSON组件所提供的转换格式
    private Date publishDate;
}
