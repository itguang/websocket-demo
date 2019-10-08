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
public class GreetingController extends WSController {

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message, Principal principal, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        String principalName = principal.getName();
        // 在此可以获取 HTTP Session 放入的信息 @see
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        return new Greeting(principalName + ":" + HtmlUtils.htmlEscape(message.getMessage()));
    }

    @MessageMapping("/helloToUser")
    @SendToUser("/topic/greetings") //  返回给自己
    public Greeting greetingToUser(HelloMessage message, Principal principal, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        String principalName = principal.getName();
        // 在此可以获取 HTTP Session 放入的信息 @see
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        return new Greeting(principalName + ":" + HtmlUtils.htmlEscape(message.getMessage()));
    }

    @GetMapping("/say/{word}")
    @ResponseBody
    public void greet(@PathVariable String word, HttpSession httpSession) {
        httpSession.setAttribute("hello", "到底有没得???????");
        httpSession.setAttribute("clinicId", 666);

        // TODO 发送给订阅了 /topic/workbench/{clinicId}}/{Role} 地址的客户端
        template.convertAndSend("/topic/workbench/999/888", new Greeting("Hello, " + HtmlUtils.htmlEscape(word) + "!"));
    }

}
