import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler


class TeaLookStuPracticeRequestHandler(tornado.web.RequestHandler):
    def get(self):
        """
        获取某个学生某个练习题，返回给老师客户端
        """
        try:
            self.sqlhandler = None
            self.stuScoreExamDetail = dict
            self.practiceId = self.get_argument("practiceId")
            self.stuId = self.get_body_argument("stuId")
            if self.getStuPractice():
                print(self.stuScoreExamDetail)
                self.write(self.stuScoreExamDetail)
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

    def getStuPractice(self):
        """
        返回某个学生某个练习题
        """
        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')

        if self.sqlhandler.getConnection():
            """
            查询练习题和成绩
            """

            sql = """select ScoreDetail,FullScore from SCORE where PracticeId='{0}' and StuId='{1}'""".format(
                self.practiceId, self.stuId)
            # print(sql)
            self.stuScore = dict(self.sqlhandler.executeQuerySQL(sql)[0])

            sql = """select ExamDetail from PRACTICE where PracticeId='{0}'""".format(
                self.practiceId)
            self.stuExam = dict(self.sqlhandler.executeQuerySQL(sql)[0])

            self.stuScoreExamDetail.update(self.stuScore, self.stuExam)
        
            return True
        return False


if __name__ == "__main__":

    app = tornado.web.Application(
        handlers=[(r"/", TeaLookStuPracticeRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
