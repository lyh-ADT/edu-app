import tornado
from TempStaticRequestHandler import TempStaticRequestHandler
from LoginHandler import LoginHandler

app = tornado.web.Application(handlers=[
    (r".+?\.(html|css|js)[\.map]*$", TempStaticRequestHandler),
    (r"/teacher-stream/login", LoginHandler)
])
http_server = tornado.httpserver.HTTPServer(app)
http_server.listen(7001)
tornado.ioloop.IOLoop.current().start()