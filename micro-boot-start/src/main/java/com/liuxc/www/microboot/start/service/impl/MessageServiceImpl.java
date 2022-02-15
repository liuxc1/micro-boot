package com.liuxc.www.microboot.start.service.impl;

import com.liuxc.www.microboot.start.service.IMessageService;

public class MessageServiceImpl implements IMessageService {

    @Override
    public String echo(String msg) {
        return msg;
    }
}
