package com.xnpe.chat.config.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.security.Principal;
import java.util.Map;

import static org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor.HTTP_SESSION_ID_ATTR_NAME;

/**
 * @author guang
 * @since 2019/10/8 11:25 上午
 */
@Component
@Slf4j
public class WebSocketEventListener {

    @EventListener
    public void handleConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

//        Message<byte[]> message = event.getMessage();
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();

        // 获取session信息
        Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
        Principal principal = accessor.getUser();

        log.info("SessionConnectEvent.onApplicationEvent: +++++++++++++++++++++++++++++++");
        log.warn("建立Socket连接事件(校验订阅安全性)-HTTP_SESSION_ID={},连接用户= {}",
                sessionAttributes.get(HTTP_SESSION_ID_ATTR_NAME), principal.toString());
    }

    @EventListener
    public void handleDisconnectListener(SessionDisconnectEvent event) {
        Message<byte[]> message = event.getMessage();
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();

        // 获取session信息
        Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
        Principal principal = accessor.getUser();

        log.warn("用户断开连接事件,sessionId={},HTTP_SESSION_ID={},连接用户= {}",
                sessionId, sessionAttributes.get(HTTP_SESSION_ID_ATTR_NAME), principal.toString());
    }

    @EventListener
    public void handleSubscribeEvent(SessionSubscribeEvent event) {

        Message<byte[]> message = event.getMessage();
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();

        // 获取session信息
        Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
        Principal principal = accessor.getUser();

        log.info("+++++++++++++++++++++++=");
        log.warn("订阅通知事件-StompHeader:Destination={},SubscriptionId={},HTTP_SESSION_ID={}", accessor.getDestination(),
                accessor.getSubscriptionId(), sessionAttributes.get(HTTP_SESSION_ID_ATTR_NAME));

    }

}
