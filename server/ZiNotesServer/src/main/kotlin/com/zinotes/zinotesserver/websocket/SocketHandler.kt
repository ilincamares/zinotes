package com.zinotes.zinotesserver.websocket

import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.CopyOnWriteArrayList
import org.slf4j.LoggerFactory

@Component
class SocketHandler: TextWebSocketHandler() {
    private val sessions = CopyOnWriteArrayList<WebSocketSession>()
    private val logger = LoggerFactory.getLogger(SocketHandler::class.java)

    override fun afterConnectionEstablished(session: WebSocketSession) {
        sessions.add(session)
        logger.info("New device connected: ${session.id}. Total devices: ${sessions.size}")
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessions.remove(session)
        logger.info("Device disconnected: ${session.id}. Status: $status")
    }

    fun broadcast(message: String) {
        sessions.forEach { session ->
            if(session.isOpen){
                try{
                    session.sendMessage(TextMessage(message))
                } catch(e: Exception){
                    logger.error("Failed to send message to session ${session.id}", e)
                }
            }
        }
    }

}