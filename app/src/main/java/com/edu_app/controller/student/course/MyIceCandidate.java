package com.edu_app.controller.student.course;

import org.webrtc.IceCandidate;

public class MyIceCandidate extends IceCandidate {
    public MyIceCandidate(String sdpMid, int sdpMLineIndex, String sdp) {
        super(sdpMid, sdpMLineIndex, sdp);
    }

    public void onIceCandidate(IceCandidate iceCandidate) {}

}
