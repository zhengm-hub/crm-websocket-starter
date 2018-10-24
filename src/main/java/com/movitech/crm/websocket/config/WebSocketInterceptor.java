package com.movitech.crm.websocket.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by zhming on 2018/10/24.
 * 握手拦截
 */
public class WebSocketInterceptor implements HandshakeInterceptor {
    public static final Logger logger = LoggerFactory.getLogger(MyHandler.class);

    /**
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @param webSocketHandler
     * @param map:后续webSocketSession的Attributes
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        ServletServerHttpRequest request = (ServletServerHttpRequest) serverHttpRequest;
        HttpSession session = request.getServletRequest().getSession();
        // WebSocketSession 赋属性值
        logger.debug("拦截所有请求绑定用户id和webSocketSession的对应关系");
        map.put("userId", session.getAttribute("userId"));
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
    }
}
