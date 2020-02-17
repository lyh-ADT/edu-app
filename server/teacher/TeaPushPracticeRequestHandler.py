import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler


class TeaPushPracticeRequestHandler(tornado.web.RequestHandler):
    def post(self):
        """
        将老师上传的题目存到数据库
        """
        try:
            self.sqlhandler = None
            self.practiceId = self.get_body_argument("PracticeId")
            self.fullScore = self.get_body_argument("FullScore")
            self.examDetail = self.get_body_argument("ExamDetail")
            print(self.practiceId)
            if self.pushPractice():
                self.write("success")
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

    def pushPractice(self):
        """
        从数据库读取学生信息
        """
        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')
        if self.sqlhandler.getConnection():
            """
            插入信息
            """
            sql = """INSERT INTO PRACTICE(PracticeId,ExamDetail,FullScore) VALUES('{0}','{1}','{2}')""".format(
                self.practiceId, self.examDetail, self.fullScore)
            if self.sqlhandler.executeOtherSQL(sql):
                return True
        return False


if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/", TeaPushPracticeRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
