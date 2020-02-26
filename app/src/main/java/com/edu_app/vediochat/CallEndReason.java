package com.edu_app.vediochat;
public enum CallEndReason {
    Busy,
    SignalError,
    Hangup,
    MediaError,
    RemoteHangup,
    OpenCameraFailure,
    Timeout,
    AcceptByOtherClient;

    private CallEndReason() {
    }
}
