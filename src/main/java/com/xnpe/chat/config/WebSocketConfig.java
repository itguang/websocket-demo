package com.xnpe.chat.config;

import com.xnpe.chat.config.handler.MyHandler;
import com.xnpe.chat.config.intercepts.MyClientInboundChannel;
import com.xnpe.chat.config.intercepts.MyHandShakeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
// 此注解表示使用STOMP协议来传输基于消息代理的消息，此时可以在@Controller类中使用@MessageMapping
@EnableWebSocketMessageBroker
@EnableWebSocket
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Autowired
    MyClientInboundChannel myClientInboundChannel;

    @Autowired
    MyHandler myHandler;

    @Autowired
    MyHandShakeInterceptor myHandShakeInterceptor;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 配置消息代理,启动简单Broker，消息的发送的地址符合配置的前缀来的消息才发送到这个broker
        registry.enableSimpleBroker("/queue", "/topic","/his/workbench/{clinicId}/{Role}");
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        //添加STOMP协议的端点。这个HTTP URL是供WebSocket或SockJS客户端访问的地址
        stompEndpointRegistry.addEndpoint("/Chat", "/ws")
//                .setHandshakeHandler()
                // HTTP 建立 webSocket请求时,把 HTTPSession中的值拷贝到 WebSocket中
                // 我们可以通过请求信息，比如token、或者session判用户是否可以连接，这样就能够防范非法用户
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                // 拦截websocket的握手请求,这个拦截器用来管理握手和握手后的事情，我们可以通过请求信息，比如token、或者session判用户是否可以连接，这样就能够防范非法用户
                // OriginHandshakeInterceptor：检查Origin头字段的合法性
                .addInterceptors(myHandShakeInterceptor)
                // 跨域设置
                .setAllowedOrigins("*","https://clinic.aihaisi.com")
                // 指定端点使用SockJS协议
                .withSockJS();

    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(myClientInboundChannel);

    }

}
