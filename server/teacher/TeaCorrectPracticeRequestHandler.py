import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler


class TeaCorrectPracticeRequestHandler(tornado.web.RequestHandler):
    def post(self):
        """
        将老师上传的题目存到数据库
        """
        try:
            self.sqlhandler = None
            self.stuId = self.get_body_argument("stuId")
            self.stuScore = self.get_body_argument("stuScore")
            self.practiceId = self.get_body_argument("practiceId")
            self.scoreDetail = self.get_body_argument("scoreDetail")

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
        将分数成绩存放到数据库
        """
        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')
        if self.sqlhandler.getConnection():
            """
            插入信息
            """
            sql = """INSERT INTO SCORE(PracticeId,StuId,StuScore,ScoreDetail) VALUES('{0}','{1}','{2}','{3}')""".format(
                self.practiceId, self.stuId, self.stuScore, self.scoreDetail)
            if self.sqlhandler.executeOtherSQL(sql):
                return True
        return False


if __name__ == "__main__":

    app = tornado.web.Application(
        handlers=[(r"/", TeaCorrectPracticeRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
