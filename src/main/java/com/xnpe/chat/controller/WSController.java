package com.xnpe.chat.controller;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.stereotype.Controller;

/**
 * @author guang
 * @since 2019/10/8 11:21 上午
 */
@Controller
public abstract class WSController {

    @MessageExceptionHandler(Exception.class)
    public Object handleWSException(Exception exception) {
        // ...
        return "Xxx";
    }

}
