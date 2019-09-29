package com.xnpe.chat.config.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

/**
 * @author guang
 * @since 2019/9/26 11:21 上午
 */
@Configuration
@Slf4j
public class MySessionConnectEvent implements ApplicationListener<SessionConnectEvent> {
    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        
        log.info("SessionConnectEvent.onApplicationEvent: ");
    }

}
