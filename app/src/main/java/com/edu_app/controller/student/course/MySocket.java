package com.edu_app.controller.student.course;


import android.util.Log;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;
import org.webrtc.PeerConnection;

import java.net.URI;
import java.util.Iterator;
import java.util.Map;

public class MySocket extends WebSocketClient {
    private WebSocket webSocket;
    private PeerConnection peerConnection;

    public MySocket(URI serverUri, Draft protocolDraft, int connectTimeout, Map<String, String> httpHeaders) {
        super(serverUri, protocolDraft, httpHeaders, connectTimeout);
    }
    public void setPeerConnection(PeerConnection peerConnection){
        this.peerConnection = peerConnection;
    }

    @Override
    public void onOpen(ServerHandshake shake) {
        Log.e("Socket","握手信号");
        for(Iterator<String> it = shake.iterateHttpFields(); it.hasNext();) {
            String key = it.next();
            Log.e("Socket",key+":"+shake.getFieldValue(key));
        }
    }

    @Override
    public void onMessage(String message) {
        Log.e("Socket","接收到消息："+message);


    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.e("Socket","关闭连接...");

    }

    @Override
    public void onError(Exception e) {
        Log.e("Socket","异常" + e);

    }


}
