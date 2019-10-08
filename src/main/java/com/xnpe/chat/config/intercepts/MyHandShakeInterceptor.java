package com.xnpe.chat.config.intercepts;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 使用HTTP建立webSocket连接时的拦截器
 *
 * @author guang
 * @since 2019/9/29 5:22 下午
 */
@Component
@Slf4j
public class MyHandShakeInterceptor implements HandshakeInterceptor {

    // 注意: 此方法中不要打断点,不然websocket连接就会无法建立

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler
            , Map<String, Object> attributes) throws Exception {
        // http协议转换websoket协议进行前，可以在这里通过session信息判断用户登录是否合法
        // TODO 在此方法校验是否可以建立 WebSocket 连接
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception exception) {
        //握手成功后
    }

    private HttpSession getSession(ServerHttpRequest request) {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
            return serverRequest.getServletRequest().getSession();
        }
        return null;
    }
}
