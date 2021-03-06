var videos = document.getElementById("videos");
var sendFileBtn = document.getElementById("sendFileBtn");
var startBtn = document.getElementById("startBtn");
var stopBtn = document.getElementById("stopBtn")
var files = document.getElementById("files");
var sendBtn = document.getElementById("sendBtn");
var msgs = document.getElementById("message_box");

var observeMode = location.search.search(/observe=true/) == 1;

var rtc = SkyRTC();
bindListeners();
/**********************************************************/
function add_meesage(message) {
    let msg_box = $("#message_box");
    let msg = $("#message_template").clone();
    msg.find(".sender").text(message.userId);
    msg.find(".content").text(message.content);

    msg_box.append(msg);
    msg_box.scrollTop(msg_box.outerHeight());
}

sendBtn.onclick = function (event) {
    var msgIpt = document.getElementById("message_input"),
    msg = msgIpt.value,
    msg = {
        'userId':rtc.userId,
        'content':msg
    };
    add_meesage(msg);
    //广播消息
    rtc.broadcast(msg);
    msgIpt.value = "";
};


var onScreenMode = false;
var startStream = false;
startBtn.onclick = function (event) {
    let courseName = document.getElementById("inputCourseName").value;
    if(courseName == ""){
        alert("请输入课程名称");
        return;
    }
    startStream = true;
    if (rtc.socket) {
        rtc.socket.close();
    } else {
        rtc.connect("wss://139.159.176.78:3000/teacher-stream/wss", window.location.hash.slice(1), observeMode, courseName);
    }
}
stopBtn.onclick = function (event) {
    if (observeMode) {
        return;
    }
    startStream = false;
    $.post("/teacher-stream/closeroom", function (result) {
        if (result.success) {
            rtc.socket && rtc.socket.close();

            // 关闭流
            if (rtc.localMediaStream)
                for (let i of rtc.localMediaStream.getTracks()) {
                    i.stop();
                }

            window.opener = null;
            window.open('', '_self', '');
            window.close();
            open(location, '_self').close();
            $("#room-number").text("房间号：已注销");
            alert("房间号已经注销");
        } else {
            console.log(result.data);
        }
    });
}

window.onclose = function () {
    if (rtc.localMediaStream)
    for (let i of rtc.localMediaStream.getTracks()) {
        i.stop();
    }
}

function addVideo(id, stream) {
    let video = document.getElementById("video_template").cloneNode(true);
    video.setAttribute("style", "");
    video.getElementsByTagName("video")[0].setAttribute("autoplay", "autoplay");
    video.setAttribute("id", id);

    videos.appendChild(video);
    rtc.attachStream(stream, id);
}

function changeVolume(img){
    let video = img.parentElement.getElementsByTagName("video")[0];
    if(video.volume <= 0){
        video.volume = 1;
        img.src = "./voice.png";
    }else{
        video.volume = 0;
        img.src = "./mute.jpg";
    }
}

function bindListeners() {
    //成功创建WebSocket连接
    rtc.on("connected", function (socket) {
        // ws心跳保持活动
        setInterval(function () {
            rtc.socket.send(JSON.stringify({
                "eventName": "__heartbeat",
                "data": {}
            }));
        }, 50000);

        //创建本地视频流
        // let mode = document.getElementById("screenMode").value;
        if (observeMode) {
            onScreenMode = false;
            rtc.createStream({
                "video": true,
                "audio": true
            });
        } else {
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
    rtc.on("stream_create_error", function (error) {
        alert(error.message);
    });
    //接收到其他用户的视频流
    rtc.on('pc_add_stream', function (stream, socketId) {
        // var newVideo = document.createElement("video");
        var id = "other-" + socketId;
        // newVideo.setAttribute("class", "other");
        // newVideo.setAttribute("autoplay", "autoplay");
        // newVideo.setAttribute("id", id);
        // newVideo.setAttribute("controls", true);
        // videos.appendChild(newVideo);
        // rtc.attachStream(stream, id);
        addVideo(id, stream);
    });
    //删除其他用户
    rtc.on('remove_peer', function (socketId) {
        var video = document.getElementById('other-' + socketId);
        if (video) {
            video.parentNode.removeChild(video);
        }
    });

    rtc.on("socket_closed", function (scoket) {
        console.log("socket_closed");
        videos.innerHTML = "";
        if (!startStream) {
            return;
        }
        rtc = SkyRTC();
        bindListeners();
        rtc.connect("wss://139.159.176.78:3000/teacher-stream/wss", window.location.hash.slice(1), observeMode);
    });

     //接收到文字信息
     rtc.on('data_channel_message', function (channel, socketId, message) {
        add_meesage(message)
    });
}