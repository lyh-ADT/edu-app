var videos = document.getElementById("videos");
var rtc = SkyRTC();


//成功创建WebSocket连接
rtc.on("connected", function (socket) {
    //创建本地视频流
    rtc.createStream({
        "video": true,
        "audio": true
    });
});
//创建本地视频流成功
rtc.on("stream_created", function (stream) {
    document.getElementById('tea_video_template').srcObject = stream;
    document.getElementById('tea_video_template').play();
    // 设置本地不播放自己的声音
    document.getElementById('tea_video_template').volume = 0.0;
});
//创建本地视频流失败
rtc.on("stream_create_error", function () {
    alert("create stream failed!");
});
//接收到其他用户的视频流
rtc.on('pc_add_stream', function (stream, socketId) {
    var newVideo = document.createElement("video");
    var id = "stu-" + socketId;
    newVideo.setAttribute("class", "video");
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

//连接WebSocket服务器
rtc.connect("wss:139.159.176.78.3000/wss", "123");
