import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler
import utils
import json



class TeaDeletePracticeRequestHandler(tornado.web.RequestHandler):
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
            self.practiceId = self.args["practiceId"]

            if self.deletePractice():
                self.write("success")
                self.finish()
            else:
                raise RuntimeError
        except Exception as e:
            print(e)
            self.write("找不到对应的练习")
            self.finish()
        finally:
            if self.sqlhandler is not None:
                self.sqlhandler.closeMySql()

    def deletePractice(self):
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
            
            sql = "delete from PRACTICE where PracticeId='{0}';".format(self.practiceId)
            sql2 = "UPDATE CLASS SET Practice=REPLACE(REPLACE(Practice, '{0}', ''), ',,', ',') WHERE ClassId='{1}';".format(self.practiceId, self.classId)
            if self.sqlhandler.executeOtherSQL(sql) and self.sqlhandler.executeOtherSQL(sql2):
                return True
        return False


if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/", TeaDeletePracticeRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
