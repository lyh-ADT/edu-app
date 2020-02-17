import tornado
from TeaPushPracticeRequestHandler import TeaPushPracticeRequestHandler

app = tornado.web.Application(handlers=[
    (r"/add_practice", TeaPushPracticeRequestHandler)
])
http_server = tornado.httpserver.HTTPServer(app)
# http_server.listen(8100)
http_server.listen(2000)
tornado.ioloop.IOLoop.current().start()
