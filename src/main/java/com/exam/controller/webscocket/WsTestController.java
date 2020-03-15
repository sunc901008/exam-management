package com.exam.controller.webscocket;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author sunc
 * @date 2019/11/25 18:33
 * @description WsTestController
 */
@RestController
public class WsTestController extends TextWebSocketHandler {
    private static final Logger logger = Logger.getLogger(WsTestController.class);

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static final CopyOnWriteArrayList<WebSocketSession> CLIENTS = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        long id = System.currentTimeMillis();
        session.getAttributes().put("id", id);

        CLIENTS.add(session);
        logger.info("Current user: " + id);
        logger.info("MsgNotice current connected number: " + CLIENTS.size());
        TextMessage returnMessage = new TextMessage("connected");
        session.sendMessage(returnMessage);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        long id = (Long) session.getAttributes().get("id");
        CLIENTS.remove(session);
        logger.info(id + " disconnect to the websocket.");
        logger.info("MsgNotice current connected number:" + CLIENTS.size());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        long id = (Long) session.getAttributes().get("id");
        session.sendMessage(new TextMessage(message.getPayload() + id));
    }
}
