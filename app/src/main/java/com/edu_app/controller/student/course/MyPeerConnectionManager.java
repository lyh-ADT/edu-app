package com.edu_app.controller.student.course;

import android.content.Context;

import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.webrtc.AudioSource;
import org.webrtc.AudioTrack;
import org.webrtc.Camera1Enumerator;
import org.webrtc.Camera2Enumerator;
import org.webrtc.CameraEnumerator;
import org.webrtc.CameraVideoCapturer;
import org.webrtc.DataChannel;
import org.webrtc.DefaultVideoDecoderFactory;
import org.webrtc.DefaultVideoEncoderFactory;
import org.webrtc.EglBase;
import org.webrtc.IceCandidate;
import org.webrtc.MediaConstraints;
import org.webrtc.MediaStream;
import org.webrtc.PeerConnection;
import org.webrtc.PeerConnectionFactory;
import org.webrtc.RtpReceiver;
import org.webrtc.RtpTransceiver;
import org.webrtc.SdpObserver;
import org.webrtc.SessionDescription;
import org.webrtc.VideoDecoderFactory;
import org.webrtc.VideoEncoderFactory;
import org.webrtc.VideoSource;
import org.webrtc.VideoTrack;
import org.webrtc.audio.JavaAudioDeviceModule;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyPeerConnectionManager implements SdpObserver,PeerConnection.Observer{
    private final static String TAG = "edu_app_WebSocket";
    private final Context context;
    private final EglBase rootEglBase;
    private VideoTrack remoteVideoTrack;
    private VideoTrack localVideoTrack;
    private MySocket webSocket;
    private ArrayList<PeerConnection.IceServer> iceServers;
    private MediaConstraints sdpConstraints;
    private MediaConstraints pcConstraints;
    private MediaConstraints audioConstraints;
    private PeerConnection peerConnection;
    private VideoSource videoSource;
    private AudioSource audioSource;
    private MediaStream mediaStream;

    public MyPeerConnectionManager(Context context) {
        this.context = context;
        this.rootEglBase = EglBase.create();
    }

    //        初始化工厂
    public PeerConnectionFactory getPeerConnectionFactory() {
        PeerConnectionFactory.initialize(
                PeerConnectionFactory.InitializationOptions.builder(context)
                        .createInitializationOptions());

        final VideoEncoderFactory encoderFactory;
        final VideoDecoderFactory decoderFactory;

        encoderFactory = new DefaultVideoEncoderFactory(
                rootEglBase.getEglBaseContext(),
                true,
                true);
        decoderFactory = new DefaultVideoDecoderFactory(rootEglBase.getEglBaseContext());

        PeerConnectionFactory.Options options = new PeerConnectionFactory.Options();
        return PeerConnectionFactory.builder()
                .setOptions(options)
                .setAudioDeviceModule(JavaAudioDeviceModule.builder(context).createAudioDeviceModule())
                .setVideoEncoderFactory(encoderFactory)
                .setVideoDecoderFactory(decoderFactory)
                .createPeerConnectionFactory();

    }


    // 获取摄像头对象
    public CameraVideoCapturer createVideoCapture() {
        CameraEnumerator enumerator;
        if (Camera2Enumerator.isSupported(this.context)) {
            enumerator = new Camera2Enumerator(this.context);
        } else {
            enumerator = new Camera1Enumerator(true);
        }
        final String[] deviceNames = enumerator.getDeviceNames();

        for (String deviceName : deviceNames) {

            CameraVideoCapturer videoCapturer = enumerator.createCapturer(deviceName, null);
            if (videoCapturer != null) {
                return videoCapturer;
            }
        }
        return null;
    }

    //   获取视频流
    public MediaStream getMediaStream(CameraVideoCapturer mVideoCapturer, PeerConnectionFactory peerConnectionFactory) {
        videoSource = peerConnectionFactory.createVideoSource(mVideoCapturer.isScreencast());
        VideoTrack videoTrack = peerConnectionFactory.createVideoTrack("videtrack", videoSource);
        audioSource = peerConnectionFactory.createAudioSource(new MediaConstraints());
        AudioTrack audioTrack = peerConnectionFactory.createAudioTrack("audiotrack", audioSource);
        mediaStream = peerConnectionFactory.createLocalMediaStream("localstream");
        mediaStream.addTrack(videoTrack);
        mediaStream.addTrack(audioTrack);
        return mediaStream;
    }

    //    获取媒体流约束
    public Map<String, MediaConstraints> getMediaConstraints() {
        pcConstraints = new MediaConstraints();
        pcConstraints.optional.add(new MediaConstraints.KeyValuePair("DtlsSrtpKeyAgreement", "true"));
        pcConstraints.optional.add(new MediaConstraints.KeyValuePair("RtpDataChannels", "true"));


        sdpConstraints = new MediaConstraints();
        sdpConstraints.mandatory.add(new MediaConstraints.KeyValuePair("OfferToReceiveAudio", "true"));
        sdpConstraints.mandatory.add(new MediaConstraints.KeyValuePair("OfferToReceiveVideo", "true"));

        audioConstraints = new MediaConstraints();
        //自动增益
        audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("googAutoGainControl", "true"));
        //噪音处理
        audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("googNoiseSuppression", "true"));
        Map map = new HashMap();
        map.put("pcConstraints", pcConstraints);
        map.put("sdpConstraints", sdpConstraints);
        map.put("audioConstraints", audioConstraints);
        return map;
    }

    //  获取Ice
    public List<PeerConnection.IceServer> getIceServer() {
        iceServers = new ArrayList<PeerConnection.IceServer>();
        iceServers.add(new PeerConnection.IceServer("stun:stun.l.google.com:19302"));
        iceServers.add(PeerConnection.IceServer.builder("stun:23.21.150.121").createIceServer());
        iceServers.add(PeerConnection.IceServer.builder("stun:stun.l.google.com:19302").createIceServer());
        return iceServers;
    }


    // 获取peerConnection
    public PeerConnection getPeerConnection(PeerConnectionFactory peerConnectionFactory) {

        peerConnection = peerConnectionFactory.createPeerConnection(getIceServer(),this);
        return peerConnection;
    }

    // 获取webSocket
    public MySocket setMyWebSocket(String url, int connectTimeout) throws URISyntaxException {
        URI uri = new URI(url);
        Draft protocolDraf = new Draft_6455();
        Map<String, String> httpHeaders = null;
        webSocket = new MySocket(uri, protocolDraf, connectTimeout, httpHeaders);
        return webSocket;
    }

    @Override
    public void onSignalingChange(PeerConnection.SignalingState signalingState) {

    }

    @Override
    public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {

    }

    @Override
    public void onIceConnectionReceivingChange(boolean b) {

    }

    @Override
    public void onIceGatheringChange(PeerConnection.IceGatheringState iceGatheringState) {

    }

    @Override
    public void onIceCandidate(IceCandidate iceCandidate) {

    }

    @Override
    public void onIceCandidatesRemoved(IceCandidate[] iceCandidates) {

    }

    @Override
    public void onAddStream(MediaStream mediaStream) {

    }

    @Override
    public void onRemoveStream(MediaStream mediaStream) {

    }

    @Override
    public void onDataChannel(DataChannel dataChannel) {

    }

    @Override
    public void onRenegotiationNeeded() {

    }

    @Override
    public void onAddTrack(RtpReceiver rtpReceiver, MediaStream[] mediaStreams) {

    }
    @Override
    public void onTrack(RtpTransceiver transceiver) {
    }
    @Override
    public void onCreateSuccess(SessionDescription sessionDescription) {

    }

    @Override
    public void onSetSuccess() {

    }

    @Override
    public void onCreateFailure(String s) {

    }

    @Override
    public void onSetFailure(String s) {

    }
}


