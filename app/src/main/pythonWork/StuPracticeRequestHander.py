import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler


class StuPracticeRequestHandler(tornado.web.RequestHandler):
    def get(self):
        """
        从数据库获取学生练习题返回给客户端

        """
        try:
            self.sqlhandler = None
            self.stuId = self.get_argument("stuId")
            self.practiceId = self.get_argument("practiceId")
            if self.getPractice():
                print(self.examDetail)
                self.write(self.examDetail)
                self.finish()
            else:
                raise RuntimeError
        except Exception:
            self.write("error")
            self.finish()
        finally:
            if self.sqlhandler is not None:
                self.sqlhandler.closeMySql()
            tornado.ioloop.IOLoop.current().stop()

    def getPractice(self):
        """
        从数据库读取学生信息
        """
        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')

        if self.sqlhandler.getConnection():
            """
            获取具体习题
            """
            sql = "select ExamDetail from PRACTICE where PracticeId='{0}'".format(
                self.practiceId)
            self.examDetail = self.sqlhandler.executeQuerySQL(sql)[0]['ExamDetail']
            return True
        return False


if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/", StuPracticeRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
