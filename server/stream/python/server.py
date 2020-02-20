import tornado
from TempStaticRequestHandler import TempStaticRequestHandler

app = tornado.web.Application(handlers=[
    (r".+?\.(html)$", TempStaticRequestHandler)
])
http_server = tornado.httpserver.HTTPServer(app)
http_server.listen(7001)
tornado.ioloop.IOLoop.current().start()