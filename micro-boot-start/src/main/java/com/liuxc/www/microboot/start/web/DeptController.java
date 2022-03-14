package com.liuxc.www.microboot.start.web;

import com.liuxc.www.microboot.start.domain.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * spring boot 处理图像数据
 */
@RestController
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private Dept dept;

    @RequestMapping("/show")
    public Dept show() {

        return this.dept;
    }

}
