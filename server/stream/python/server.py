import tornado
from TempStaticRequestHandler import TempStaticRequestHandler
from LoginHandler import LoginHandler
from RoomListHandler import RoomListHandler

app = tornado.web.Application(handlers=[
    (r"/teacher-stream/.+?\.(html|css|js)[\.map]*$", TempStaticRequestHandler),
    (r"/teacher-stream/login", LoginHandler),
    (r"/teacher-stream/list", RoomListHandler),
])
http_server = tornado.httpserver.HTTPServer(app)
http_server.listen(7001)
tornado.ioloop.IOLoop.current().start()