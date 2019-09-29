package com.xnpe.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

/**
 * @author guang
 * @since 2019/9/26 4:10 下午
 */
@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages.simpDestMatchers("/app/*").authenticated()
         .simpSubscribeDestMatchers( "/topic/*").hasRole("ADMIN");
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
