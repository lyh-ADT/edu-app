const express = require('express');
const app = express();
const cookieParser = require('cookie-parser');
const db = require('./db');
let UUID = require('node-uuid');
const server = require('http').createServer(app);
const path = require("path");
const SkyRTC = require('./public/dist/js/SkyRTC.js').listen(server);
const port = process.env.PORT || 3001;
const hostname = "0.0.0.0";

app.use(express.static(path.join(__dirname, 'public', 'dist')), null);
app.use(cookieParser());
app.use(express.urlencoded({ extended: true }));

server.listen(port, hostname, function () {
    console.log(`Server running at http://${hostname}:${port}/`);
});


app.get('/', function (req, res) {
    res.sendfile(__dirname + '/index.html');
});

app.post('/login', function(req, res){
    let info = req.body;
    let sql = "select TeaId from StreamTeaAccount where TeaId='"+ info.userId +"' and TeaPassword='"+info.userPassword+"';"
    db.select(sql, function(err, result){
        if(err){
            res.send({
                success:false,
                data:"服务器异常"
            });
            return;
        }
        if(result.length != 1){
            res.send({
                success:false,
                data:"账户或密码错误"
            });
            return;
        }
        let id = result[0].TeaId;
        let uid = UUID.v4();
        console.log("直播教师id:"+id+"登录"+uid);
        db.query("update StreamTeaAccount set TeaUid=? where TeaId=?", [uid, id], function(err, result){
            if(err){
                res.send({
                    success:false,
                    data:"服务器异常"
                });
                return;
            }
            res.cookie("UID", uid);
            res.send({
                success:true,
                data:""
            })
        })
    })
});

app.get("/list", function(req, res){
    console.log(req.cookies);
    let uid = req.cookies.UID;
    if(!uid){
        res.send("error");
        return;
    }
    db.checkUID(uid, function(err, result){
        if(err || result.length != 1){
            res.send("error");
            return;
        }
        db.select(`select TeaId, TeaName, b.CourseName, RoomNumber from 
        (select a.TeaId, TeaPersonInfo.TeaName, CourseName, RoomNumber from 
            (select TeaId, CLASS.CourseName, RoomNumber from StreamRoom left join CLASS on StreamRoom.ClassId=CLASS.ClassId) as a 
        left join TeaPersonInfo on a.TeaId=TeaPersonInfo.TeaId) as b;`, function(err, result){
        if(err){
            console.log(err);
            return;
        }
        console.log(result);
        res.send(result)
    });
    })
    
});

SkyRTC.rtc.on('new_connect', function (socket) {
    console.log('创建新连接');
});

SkyRTC.rtc.on('remove_peer', function (socketId) {
    console.log(socketId + "用户离开");
});

SkyRTC.rtc.on('new_peer', function (socket, room) {
    console.log("新用户" + socket.id + "加入房间" + room);
});

SkyRTC.rtc.on('socket_message', function (socket, msg) {
    console.log("接收到来自" + socket.id + "的新消息：" + msg);
});

SkyRTC.rtc.on('ice_candidate', function (socket, ice_candidate) {
    console.log("接收到来自" + socket.id + "的ICE Candidate");
});

SkyRTC.rtc.on('offer', function (socket, offer) {
    console.log("接收到来自" + socket.id + "的Offer");
});

SkyRTC.rtc.on('answer', function (socket, answer) {
    console.log("接收到来自" + socket.id + "的Answer");
});

SkyRTC.rtc.on('error', function (error) {
    console.log("发生错误：" + error.message);
});