package com.xnpe.chat.config.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.StompSubProtocolHandler;

/**
 * @author guang
 * @since 2019/9/26 11:21 上午
 */
@Configuration
@Slf4j
public class MySessionSubscribeEvent implements ApplicationListener<SessionSubscribeEvent> {
    @Override
    public void onApplicationEvent(SessionSubscribeEvent event) {

        log.info("SessionConnectedEvent.onApplicationEvent: ");
    }

}
