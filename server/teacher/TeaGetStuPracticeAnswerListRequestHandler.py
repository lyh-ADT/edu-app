import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler
import utils
import json


class TeaGetStuPracticeAnswerListRequestHandler(tornado.web.RequestHandler):
    def get(self):
        """
        获取练习题答案，返回给老师客户端
        """
        try:
            self.teaClassPractice = []
            self.sqlhandler = None
            if not utils.isUIDValid(self):
                self.write("no uid")
                return
            
            self.practiceId = self.get_argument("practiceId")
            self.StuId = self.get_argument("stuId")
            self.stuAnser = None
            if self.getAnswer():
                print(self.stuAnser)
                self.write(self.stuAnser if self.stuAnser is not None else "{}")
                self.finish()
            else:
                raise RuntimeError
        except Exception as e:
            print(e)
            self.write("error")
            self.finish()
        finally:
            if self.sqlhandler is not None:
                self.sqlhandler.closeMySql()

    def getAnswer(self):
        """
        查询学生练习答案
        """
        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')

        if self.sqlhandler.getConnection():
            """
            查询学生练习答案
            """
            # 获取学生答案
            sql = "select StuAnswer from SCORE WHERE PracticeId='{}' AND StuId='{}';".format(self.practiceId, self.StuId)
            classes = self.sqlhandler.executeQuerySQL(sql)[0]
            self.stuAnser = classes['StuAnswer']
            return True
        return False


if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/",
                                             TeaGetStuPracticeAnswerListRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
