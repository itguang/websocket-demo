package com.xnpe.chat.config.intercepts;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Map;

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

        log.info("preSend ...");
        SimpMessageHeaderAccessor mutableAccessor =
                (SimpMessageHeaderAccessor) SimpMessageHeaderAccessor.getMutableAccessor(message);

        log.info("preSend-发送信息之前message:{}",mutableAccessor.getDestination());
        Map<String, Object> sessionAttributes = mutableAccessor.getSessionAttributes();

        Integer clinicId = (Integer) sessionAttributes.get("clinicId");
        log.warn("clinicId={}", clinicId);

        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {

        log.info("postSend ...");
    }

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {

        log.info(" afterSendCompletion...");
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();
        if (StompCommand.SUBSCRIBE.equals(command)) {
            log.info("订阅成功:Destination={},SubscriptionId={}", accessor.getDestination(),
                    accessor.getSubscriptionId());

        }




    }

    @Override
    public boolean preReceive(MessageChannel channel) {
        log.info("preReceive ... ");

        return true;
    }

    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        log.info("postReceive ... ");
        return message;
    }

    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
        log.info("afterReceiveCompletion ... ");
    }
}
