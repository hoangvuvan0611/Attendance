package com.v1.attendance.utils.websocket;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class MyWebSocketHandler extends BinaryWebSocketHandler implements WebSocketHandler {
    ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
    ExecutorService executorService = Executors.newFixedThreadPool(2);
    FaceRecognize faceRecognize;

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        byteArrayStream.write(message.getPayload().array());

        faceRecognize = new FaceRecognize();

        if(message.isLast()){
            executorService.execute(faceRecognize);

            //return face is detected to client
            //session.sendMessage(new BinaryMessage(faceRecognize.recognize(byteArrayStream)));
            String result = faceRecognize.recognize(byteArrayStream);
            if(result != null){
                session.sendMessage(new BinaryMessage(result.getBytes()));
//                session.close();
            }

            byteArrayStream.reset();
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
    }

    @Override
    public boolean supportsPartialMessages() {
        return true;
    }
}
