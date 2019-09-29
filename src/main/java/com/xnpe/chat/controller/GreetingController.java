package com.xnpe.chat.controller;

import com.xnpe.chat.data.Greeting;
import com.xnpe.chat.data.HelloMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;

@Controller
public class GreetingController {

    // TODO 自定义
    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        // simulated delay
        Thread.sleep(1000);

        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @GetMapping("/say/{word}")
    @ResponseBody
    public void greet(@PathVariable String word, HttpSession httpSession) {
        httpSession.setAttribute("hello",123);

        template.convertAndSend("/topic/greetings", new Greeting("Hello, " + HtmlUtils.htmlEscape(word) + "!"));
    }

    @MessageMapping("/trade")
    @SendToUser("/queue/position-updates")
    public Greeting executeTrade(HelloMessage message, Principal principal) {
        // ...
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

}
