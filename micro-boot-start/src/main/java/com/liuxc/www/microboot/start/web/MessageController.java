package com.liuxc.www.microboot.start.web;

import com.liuxc.www.microboot.start.service.IMessageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private final IMessageService messageService;

    public MessageController(IMessageService messageService) {
        this.messageService = messageService;
    }

    @RequestMapping("/echo")
    public String echo(String message) {

        return "[echo]" + messageService.echo(message);
    }
}
