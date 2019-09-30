package com.xnpe.chat.config.intercepts;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Map;

import static org.springframework.messaging.simp.SimpMessageType.*;
import static org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor.HTTP_SESSION_ID_ATTR_NAME;

/**
 * 发送信息到 channel
 *
 * @author guang
 * @since 2019/9/26 4:51 下午
 */
@Component
@Slf4j
public class MyClientInboundChannel implements ChannelInterceptor {

    /**
     * 在建立WebSocket连接后就会立即执行此方法,还有(发送消息,关闭Socket连接等等)
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        log.info("preSend ===================================================");
        SimpMessageHeaderAccessor accessor =
                (SimpMessageHeaderAccessor) SimpMessageHeaderAccessor.getMutableAccessor(message);

        MessageHeaders headers = message.getHeaders();
        SimpMessageType messageType = SimpMessageHeaderAccessor.getMessageType(headers);
        // 获取session信息
        Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
        Principal principal = accessor.getUser();

        if (messageType.equals(CONNECT)) {
            // TODO 建立连接时的安全性校验
            log.warn("建立Socket连接(校验订阅安全性)-HTTP_SESSION_ID={},连接用户= {}",
                    sessionAttributes.get(HTTP_SESSION_ID_ATTR_NAME), principal.toString());
        }

        if (MESSAGE.equals(messageType)) {
            // TODO message Payload 获取???
            log.warn("客户端发送消息 - HTTP_SESSION_ID={},连接用户= {},SimpMessageHeader:Destination={}",
                    sessionAttributes.get(HTTP_SESSION_ID_ATTR_NAME), principal.toString(), accessor.getDestination());
        }

        if (messageType.equals(SUBSCRIBE)) {
            // TODO 订阅的安全性校验
            log.warn("订阅之前(校验订阅安全性)-SimpMessageHeader:Destination={},SubscriptionId={}", accessor.getDestination(),
                    accessor.getSubscriptionId());

            Integer clinicId = (Integer) sessionAttributes.get("clinicId");
            log.warn("clinicId={}", clinicId);
        }

        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {

        log.info("postSend ============================================");
    }

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {

        log.info(" afterSendCompletion ===============================");
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();
        if (StompCommand.SUBSCRIBE.equals(command)) {
            log.warn("订阅成功-StompHeader:Destination={},SubscriptionId={}", accessor.getDestination(),
                    accessor.getSubscriptionId());

        }

        MessageHeaders headers = message.getHeaders();
        SimpMessageType messageType = SimpMessageHeaderAccessor.getMessageType(headers);

        if (messageType.equals(SUBSCRIBE)) {
            log.warn("订阅成功-SimpMessageHeader:Destination={},SubscriptionId={}", accessor.getDestination(),
                    accessor.getSubscriptionId());

        }

    }

    @Override
    public boolean preReceive(MessageChannel channel) {
        log.info("preReceive ========================================= ");

        return true;
    }

    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        log.info("postReceive =================================================== ");
        return message;
    }

    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
        log.info("afterReceiveCompletion ================================================ ");
    }
}
