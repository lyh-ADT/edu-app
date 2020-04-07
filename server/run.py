# coding=utf8

# 轮询间隔（单位：秒）
INTERVAL = 10 * 60

PROGRAMS = [
    {
        "name":"admin",
        "cwd":"./admin",
        "command":"python3 -u server.py",
        "log-file":"./log/admin-log.log"
    },
    {
        "name":"login",
        "cwd":"./login",
        "command":"python3 -u LoginRequestHandler.py",
        "log-file":"./log/login-log.log"
    },
    {
        "name":"stu",
        "cwd":"./stu",
        "command":"python3 -u StuRequestHandler.py",
        "log-file":"./log/student-log.log"
    },
    {
        "name":"teacher",
        "cwd":"./teacher",
        "command":"python3 -u server.py",
        "log-file":"./log/teacher-log.log"
    },
    {
        "name":"webrtc",
        "cwd":"./webrtc/server/webrtc_server_node",
        "command":"node server.js",
        "log-file":"./log/webrtc-log.log"
    },
    {
        "name":"rtmp",
        "cwd":"./nms/Node-Media-Server",
        "command":"node app.js",
        "log-file":"./log/rtmp-log.log"
    }
]

import subprocess
import signal
import time

class NamedPopen:
    def __init__(self, params:dict):
        self.params = params
        self.name = params['name']
        self.popen = None
        self.restart()
    
    def isRunning(self):
        return self.popen.poll() is None
    
    def getStatus(self):
        return "running" if self.isRunning() else "stoped"

    def restart(self):
        params = self.params
        log = open(params['log-file'], 'ab+')
        self.popen = subprocess.Popen(params['command'], cwd=params['cwd'], stdout=log, stderr=log, shell=True, bufsize=2)

    def __str__(self):
        return 'NamedPopen(name:{}, pid:{}, status:{})'.format(self.name, self.popen.pid, self.getStatus())

    def __repr__(self):
        return str(self)

if __name__ == '__main__':
    runnings = []
    
    # 启动程序
    for i in PROGRAMS:
        runnings.append(NamedPopen(i))

    def stop_all(signum, frame):
        for i in runnings:
                i.popen.kill()
        print("信号signum:", signum)
        exit(-1)
    
    signal.signal(signal.SIGINT, stop_all)
    signal.signal(signal.SIGTERM, stop_all)
    signal.signal(signal.SIGABRT, stop_all)
    signal.signal(signal.SIGIOT, stop_all)

    while True:
        print("{}\t开始轮询状态".format(time.strftime("%Y-%m-%d %H:%M:%S", time.localtime())))

        for i in runnings:
            if i.isRunning():
                print(i)
            else:
                print(i, "停止，重启...")
                i.restart()
                if i.isRunning():
                    print(i, "重启成功")
                else:
                    print("!!!错误，", i, "重启失败")

        time.sleep(INTERVAL)


