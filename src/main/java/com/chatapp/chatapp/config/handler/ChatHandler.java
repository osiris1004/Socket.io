package com.chatapp.chatapp.config.handler;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
public class ChatHandler implements WebSocketHandler {

    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Connection established on session: {}", session.getId());
        System.out.println("############Connection established on session: {}"+ session.getId());
        sessions.add(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String chatMessage = (String) message.getPayload();
        //session.sendMessage(new TextMessage("Message From User: "+session.getId()+"=>" + chatMessage));
       // System.out.println("############Started processing chat: " + chatMessage);
       broadcastMessage(chatMessage);
     
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.info("Exception occured: {} on session: {}", exception.getMessage(), session.getId());
        System.out.println("############Exception occured: {} on session: {}: " + exception.getMessage()+ session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.info("Connection closed on session: {} with status: {}", session.getId(), closeStatus.getCode());
        System.out.println("############Connection closed on session: {} with status: {}: " + session.getId()+ closeStatus.getCode());
        sessions.remove(session);
    }


    private void broadcastMessage(String message) {
        // Iterate through all connected sessions and send the message
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage("Broadcast Message From User" +session.getId()+"=>"+ message));
            } catch (IOException e) {
                log.error("Error broadcasting message to session: {}", session.getId(), e);
            }
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}