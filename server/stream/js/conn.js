var videos = document.getElementById("videos");
var sendBtn = document.getElementById("sendBtn");
var msgs = document.getElementById("message_box");
var sendFileBtn = document.getElementById("sendFileBtn");
var startBtn = document.getElementById("startBtn")
var files = document.getElementById("files");
var rtc = SkyRTC();
bindListeners();
/**********************************************************/
sendBtn.onclick = function (event) {
    var msgIpt = document.getElementById("msgIpt"),
        msg = msgIpt.value,
        p = document.createElement("p");
    p.innerText = "me: " + msg;
    //广播消息
    rtc.broadcast(msg);
    msgIpt.value = "";
    msgs.appendChild(p);
};
var onScreenMode = false;
startBtn.onclick = function (event) {
    if (rtc.socket) {
        rtc.socket.close();
    } else {
        rtc.connect("wss://139.159.176.78:3000/teacher-stream/wss", window.location.hash.slice(1));
    }
}

function bindListeners() {
    //成功创建WebSocket连接
    rtc.on("connected", function (socket) {
        //创建本地视频流
        let mode = document.getElementById("screenMode").value;
        if (mode == "cam") {
            rtc.createStream({
                "video": true,
                "audio": true
            });
        } else if (mode == "scr") {
            onScreenMode = true;
            rtc.createScreenStream({
                video: {
                    cursor: "always"
                },
                audio: true
            });
        }
    });
    //创建本地视频流成功
    rtc.on("stream_created", function (stream) {
        document.getElementById('me').srcObject = stream;
        document.getElementById('me').play();
        // 设置本地不播放自己的声音
        document.getElementById('me').volume = 0.0;
    });
    //创建本地视频流失败
    rtc.on("stream_create_error", function () {
        alert("create stream failed!");
    });
    //接收到其他用户的视频流
    rtc.on('pc_add_stream', function (stream, socketId) {
        var newVideo = document.createElement("video");
        var id = "other-" + socketId;
        newVideo.setAttribute("class", "other");
        newVideo.setAttribute("autoplay", "autoplay");
        newVideo.setAttribute("id", id);
        videos.appendChild(newVideo);
        rtc.attachStream(stream, id);
    });
    //删除其他用户
    rtc.on('remove_peer', function (socketId) {
        var video = document.getElementById('other-' + socketId);
        if (video) {
            video.parentNode.removeChild(video);
        }
    });
    //接收到文字信息
    rtc.on('data_channel_message', function (channel, socketId, message) {
        var p = document.createElement("p");
        p.innerText = socketId + ": " + message;
        msgs.appendChild(p);
    });

    rtc.on("socket_closed", function (scoket) {
        console.log("socket_closed");
        videos.innerHTML = "";
        rtc = SkyRTC();
        bindListeners();
        rtc.connect("wss://139.159.176.78:3000/teacher-stream/wss", window.location.hash.slice(1));
    });
}