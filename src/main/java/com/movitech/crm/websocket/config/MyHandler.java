package com.movitech.crm.websocket.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhming on 2018/10/24.
 */
public class MyHandler extends AbstractWebSocketHandler {

    public static final Logger logger = LoggerFactory.getLogger(MyHandler.class);

    //在线用户列表
    public static final Map<Integer, WebSocketSession> users = new ConcurrentHashMap();
    //用户标识
    private static final String USER_ID = "userId";

    /**
     * 收到消息时触发的回调
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);
    }

    /**
     * 建立连接后触发的回调
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 客户端用户id
        Integer userId = getClientId(session);
        // 用户id和webSocketSession一一对应
        users.put(userId, session);
        logger.debug("webSocket连接建立,用户id:{},webSocketSessionId:{}", userId, session.getId());
    }


    /**
     * 传输消息出错时触发的回调
     *
     * @param session
     * @param exception
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        Integer userId = getClientId(session);
        logger.debug("消息传输出错,关闭连接,用户id:{},webSocketSessionId:{}", userId, session.getId());
        if (session.isOpen()) {
            session.close();
        }
        users.remove(userId);
    }

    /**
     * 断开连接后触发的回调
     *
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Integer userId = getClientId(session);
        logger.debug("webSocket连接断开,用户id:{},webSocketSessionId:{}", userId, session.getId());
    }

    /**
     * 是否处理分片消息
     *
     * @return
     */
    @Override
    public boolean supportsPartialMessages() {
        return super.supportsPartialMessages();
    }

    /**
     * @param
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Integer userId = getClientId(session);
        logger.debug("收到来自用户(userId/webSocketSessionId):{}的文本信息:{}", userId + "/" + session.getId(), message.getPayload());
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        Integer userId = getClientId(session);
        logger.debug("收到来自用户(userId/webSocketSessionId):{}的二进制信息:{}", userId + "/" + session.getId(), message.getPayload());
    }

    /**
     * Ping 和 Pong消息的作用：
     * 1）两种消息通常被用来检查WebSocket连接的健康性，（连接是否有效）。
     * 2）可以通过测量Ping和Pong消息所花费的时间来测算WebSocket连接的效率。
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        Integer userId = getClientId(session);
        logger.debug("收到来自用户(userId/webSocketSessionId):{}的PONG信息:{}", userId + "/" + session.getId(), message.getPayload());
    }

    /**
     * 获取用户标识
     *
     * @param session
     * @return
     */
    private Integer getClientId(WebSocketSession session) {
        Integer clientId = (Integer) session.getAttributes().get(USER_ID);
        return clientId;
    }
}
