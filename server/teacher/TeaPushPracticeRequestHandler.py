import tornado.ioloop
import tornado.web
import tornado.httpclient
import utils
import json
import sys
sys.path.append("..")
import SqlHandler


class TeaPushPracticeRequestHandler(tornado.web.RequestHandler):
    def post(self):
        """
        将老师上传的题目存到数据库
        """
        try:
            self.sqlhandler = None
            self.args = {}
            if not utils.isUIDValid(self):
                self.write("need login")
                return

            utils.parseJsonRequestBody(self)
            self.classId = self.args["classId"]
            self.practiceId = self.args["title"]
            self.fullScore = self.args["fullScore"]
            questions = self.args["questions"]
            self.examDetail = []
            self.answers = []
            for i in questions:
                self.answers.append({
                    "orderNumber":i["orderNumber"],
                    "answer":i.pop("answer")
                })
                self.examDetail.append(i)
            self.examDetail = json.dumps(self.examDetail)
            self.answers = json.dumps(self.answers)

            if self.pushPractice():
                self.write("success")
                self.finish()
            else:
                raise RuntimeError
        except Exception as e:
            print(e)
            self.write("请检查练习标题是否重复")
            self.finish()
        finally:
            if self.sqlhandler is not None:
                self.sqlhandler.closeMySql()

    def pushPractice(self):
        """
        从数据库读取学生信息
        """
        self.sqlhandler = SqlHandler.SqlHandler()
        if self.sqlhandler.getConnection():
            """
            插入信息
            """
            sql = """INSERT INTO PRACTICE(PracticeId,ExamDetail,FullScore, TeaAnswer) VALUES('{0}','{1}','{2}', '{3}')""".format(
                self.practiceId, str(self.examDetail).replace("'", "\\'"), self.fullScore, str(self.answers).replace("'", "\\'"))
            if self.sqlhandler.executeOtherSQL(sql):
                sql = "UPDATE CLASS SET Practice=CONCAT_WS(',', Practice, '{0}') WHERE ClassId='{1}';".format(self.practiceId, self.classId)
                print(sql)
                if self.sqlhandler.executeOtherSQL(sql):
                    return True
        return False


if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/", TeaPushPracticeRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
