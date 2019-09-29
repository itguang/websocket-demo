package com.xnpe.chat.config.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.StompSubProtocolHandler;

/**
 * @author guang
 * @since 2019/9/26 11:21 上午
 */
@Configuration
@Slf4j
public class MyBrokerAvailabilityListener implements ApplicationListener<BrokerAvailabilityEvent> {
    @Override
    public void onApplicationEvent(BrokerAvailabilityEvent event) {

        log.info("SessionConnectedEvent.onApplicationEvent...");    }

}
