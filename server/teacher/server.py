import tornado
from TeaGetClassListRequestHandler import TeaGetClassListRequestHandler
from TeaGetPracticeListRequestHandler import TeaGetPracticeListRequestHandler
from TeaPushPracticeRequestHandler import TeaPushPracticeRequestHandler

app = tornado.web.Application(handlers=[
    (r"/classList", TeaGetClassListRequestHandler),
    (r"/practice", TeaGetPracticeListRequestHandler),
    (r"/add_practice", TeaPushPracticeRequestHandler)
])
http_server = tornado.httpserver.HTTPServer(app)
# http_server.listen(8100)
http_server.listen(2000)
tornado.ioloop.IOLoop.current().start()
