package com.liuxc.www.microboot.start.web;

import com.liuxc.www.microboot.start.domain.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fieldInject")
public class FieldInjectController {
    @Autowired
    private Source source;

    @RequestMapping("/show")
    public Object show() {

        return source;
    }

}
