package com.xnpe.chat.controller;

import com.xnpe.chat.data.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void handleChat(Principal principal, Info info) {
        if (principal.getName().equals("user1")) {
            messagingTemplate.convertAndSendToUser("user2",
                    "/queue/notification", principal.getName() + " send message to you: "
                            + info.getInfo());
        } else {
            messagingTemplate.convertAndSendToUser("user1",
                    "/queue/notification", principal.getName() + " send message to you: "
                            + info.getInfo());
        }
    }
}
