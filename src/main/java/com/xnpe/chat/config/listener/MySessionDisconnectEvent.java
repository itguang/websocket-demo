package com.xnpe.chat.config.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;
import java.util.Map;

import static org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor.HTTP_SESSION_ID_ATTR_NAME;

/**
 * @author guang
 * @since 2019/9/26 11:21 上午
 */
@Configuration
@Slf4j
public class MySessionDisconnectEvent implements ApplicationListener<SessionDisconnectEvent> {
    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        Message<byte[]> message = event.getMessage();
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();

        // 获取session信息
        Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
        Principal principal = accessor.getUser();

        log.warn("用户断开连接事件,sessionId={},HTTP_SESSION_ID={},连接用户= {}",
                sessionId, sessionAttributes.get(HTTP_SESSION_ID_ATTR_NAME), principal.toString());

    }

}
