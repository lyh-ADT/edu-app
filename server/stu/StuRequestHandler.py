from StuClassRequestHandler import StuClassRequestHandler
from StuSetClassRequestHandler import StuSetClassRequestHandler
from StuInfoRequestHandler import StuInfoRequestHandler
from StuSetInfoRequestHandler import StuSetInfoRequestHandler
from StuPracticeListRequestHandler import StuPracticeListRequestHandler
from StuCheckPasswordRequestHandler import StuCheckPasswordRequestHandler
from StuLookPracticeRequestHandler import StuLookPracticeRequestHandler
from StuPracticeRequestHandler import StuPracticeRequestHandler
from StuPostAnswerRequestHandler import StuPostAnswerRequestHandler
import tornado.ioloop
import tornado.web
import tornado.httpclient
from StuGetVedioListRequestHandler import StuGetVedioListRequestHandler
app = tornado.web.Application(handlers=[(
    r"/stuGetClass",
    StuClassRequestHandler), (
        r"/stuSetClass",
        StuSetClassRequestHandler), (
            r"/stuGetInfo",
            StuInfoRequestHandler), (
                r"/stuSetInfo", StuSetInfoRequestHandler
            ), (r"/stuGetPracticeList", StuPracticeListRequestHandler
                ), (r"/stuCheckPassword", StuCheckPasswordRequestHandler
                    ), (r"/stuGetPracticeToLook", StuLookPracticeRequestHandler
                        ), (r"/stuGetPracticeToDo", StuPracticeRequestHandler
                            ), (r"/stuPostAnswer",
                                StuPostAnswerRequestHandler),
                                        (r"/stuGetVedioList",
                                         StuGetVedioListRequestHandler)])
http_server = tornado.httpserver.HTTPServer(app)
http_server.listen(8081)
tornado.ioloop.IOLoop.current().start()
