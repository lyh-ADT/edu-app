import tornado
from TeaGetClassListRequestHandler import TeaGetClassListRequestHandler
from TeaGetPracticeListRequestHandler import TeaGetPracticeListRequestHandler
from TeaPushPracticeRequestHandler import TeaPushPracticeRequestHandler
from TeaDeletePracticeRequestHandler import TeaDeletePracticeRequestHandler
from TeaInfoRequestHandler import TeaInfoRequestHandler
from TeaCheckPasswordRequestHandler import TeaCheckPasswordRequestHandler
from TeaSetInfoRequestHandler import TeaSetInfoRequestHandler
from TeaCorrectPracticeRequestHandler import TeaCorrectPracticeRequestHandler
from TeaGetPracticeRequestHandler import TeaGetPracticeRequestHandler
from TeaGetStuPracticeAnswerListRequestHandler import TeaGetStuPracticeAnswerListRequestHandler

app = tornado.web.Application(handlers=[
    (r"/classList", TeaGetClassListRequestHandler),
    (r"/practice", TeaGetPracticeListRequestHandler),
    (r"/add_practice", TeaPushPracticeRequestHandler),
    (r"/delete_practice", TeaDeletePracticeRequestHandler),
    (r"/judge_practice_list", TeaGetPracticeRequestHandler),
    (r"/judge_student", TeaGetStuPracticeAnswerListRequestHandler),
    (r"/judge_practice", TeaCorrectPracticeRequestHandler),
    (r"/teaGetInfo", TeaInfoRequestHandler),
    (r"/teaCheckPassword", TeaCheckPasswordRequestHandler),
    (r"/teaSetInfo", TeaSetInfoRequestHandler)
])
http_server = tornado.httpserver.HTTPServer(app)
# http_server.listen(8100)
http_server.listen(2000)
tornado.ioloop.IOLoop.current().start()
