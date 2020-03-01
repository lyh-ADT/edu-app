package com.edu_app.vediochat;
public enum CallState {
    Idle,
    Outgoing,
    Incoming,
    Connecting,
    Connected;

    private CallState() {
    }
}