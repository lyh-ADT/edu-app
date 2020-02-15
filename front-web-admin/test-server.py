from http.server import BaseHTTPRequestHandler
import database
import json

class Handler(BaseHTTPRequestHandler):

    def do_GET(self):
        if self.path == '/admin/login.html':
            self.send_response(200)
            self.end_headers()
            with open("admin-login.html", 'rb') as f:
                self.wfile.write(f.read())
        elif self.path == '/admin':
            self.send_response(200)
            self.end_headers()
            with open("admin.html", 'rb') as f:
                self.wfile.write(f.read())
        elif self.path == '/admin/student':
            self.send_response(200)
            self.end_headers()
            with open("student.html", 'rb') as f:
                self.wfile.write(f.read())
        elif self.path == '/admin/class':
            self.send_response(200)
            self.end_headers()
            with open("class.html", 'rb') as f:
                self.wfile.write(f.read())
        elif self.path == '/admin/teacher':
            self.send_response(200)
            self.end_headers()
            with open("teacher.html", 'rb') as f:
                self.wfile.write(f.read())
        elif self.path == '/admin/bootstrap.min.css':
            self.send_response(200)
            self.send_header("Content-type", "text/css; charset=utf-8")
            self.end_headers()
            with open("bootstrap.min.css", 'rb') as f:
                self.wfile.write(f.read())

    def do_POST(self):
        rlen = int(self.headers['Content-Length'])
        data = self.rfile.read(rlen)
        print(data)
        if self.path == '/admin/studenttable':
            self.send_response(200)
            self.end_headers()
            j = json.dumps(database.studentList)
            self.wfile.write(bytes(j, "utf8"))
        elif self.path == '/admin/teachertable':
            self.send_response(200)
            self.end_headers()
            j = json.dumps(database.teacherList)
            self.wfile.write(bytes(j, "utf8"))
        elif self.path == '/admin/classtable':
            self.send_response(200)
            self.end_headers()
            j = json.dumps(database.classList)
            self.wfile.write(bytes(j, "utf8"))




if __name__ == "__main__":
    from http.server import HTTPServer
    server_address = ('192.168.123.22', 3000)
    server = HTTPServer(server_address, Handler)
    print(server_address, "服务器启动...")
    server.serve_forever()
