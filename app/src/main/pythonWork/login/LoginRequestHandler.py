from TeaStuLoginRequestHandler import TeaStuLoginRequestHandler

import tornado.ioloop
import tornado.web
import tornado.httpclient
app = tornado.web.Application(handlers=[(
    r"/login",
    TeaStuLoginRequestHandler)])
http_server = tornado.httpserver.HTTPServer(app)
http_server.listen(8080)
tornado.ioloop.IOLoop.current().start()
