# coding=utf8

PROGRAMS = [
    {
        "name":"admin",
        "path":"./admin",
        "command":"python3 server.py",
        "log-file":"log.log"
    },
    {
        "name":"login",
        "path":"./login",
        "command":"python3 server.py",
        "log-file":"log.log"
    },
    {
        "name":"stu",
        "path":"./stu",
        "command":"python3 server.py",
        "log-file":"log.log"
    },
    {
        "name":"teacher",
        "path":"./teacher",
        "command":"python3 server.py",
        "log-file":"log.log"
    },
    {
        "name":"webrtc",
        "path":"./webrtc",
        "command":"node server.js",
        "log-file":"log.log"
    }
]

import subprocess

class NamedPopen:
    def __init__(self, params:dict):
        self.name = params['name']
        self.popen = subprocess.Popen(params['commands'], cwd=params['path'], stdout=params['log-file'], stderr=params['log-file'])

p = subprocess.Popen(['dir','db.ini'], shell=True)
p.wait()
print(p)