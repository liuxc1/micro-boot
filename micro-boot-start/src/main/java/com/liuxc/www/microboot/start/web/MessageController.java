package com.liuxc.www.microboot.start.web;


import com.liuxc.www.microboot.start.comm.abs.AbstractBaseController;
import com.liuxc.www.microboot.start.domain.Message;
import com.liuxc.www.microboot.start.domain.User;
import com.liuxc.www.microboot.start.service.IMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MessageController extends AbstractBaseController {

    private final IMessageService messageService;

    public MessageController(IMessageService messageService) {
        this.messageService = messageService;
    }

    @RequestMapping("/echo")
    public String echo(String message) {

        return "[echo]" + messageService.echo(message);
    }

    @RequestMapping("/echo2")
    public Object echo2(Message message) {

        return message;
    }

    @RequestMapping("/echo3")
    public Object echo3(User user) {

        return user;
    }

}
