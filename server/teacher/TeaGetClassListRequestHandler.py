import tornado.ioloop
import tornado.web
import tornado.httpclient
import utils
import json
import sys
sys.path.append("..")
import SqlHandler


class TeaGetClassListRequestHandler(tornado.web.RequestHandler):
    def get(self):
        """
        获取练习题列表，返回给老师客户端
        """
        try:
            self.teaClass = []
            self.sqlhandler = None
            if not utils.isUIDValid(self):
                self.write("no uid")
                return

            if self.getTeaClass():
                self.write(
                    json.dumps(self.teaClass
                               ) if len(self.teaClass) != 0 else "[]")
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

    def getTeaClass(self):
        """
        返回老师的习题列表
        """
        self.sqlhandler = SqlHandler.SqlHandler()

        if self.sqlhandler.getConnection():
            """
            查询班级列表
            """
            # 获取班级id
            sql = "select ClassId as classId, CourseName from CLASS where ClassId in (select ClassId from ClassTeaRelation where TeaId=(select teaId from TeaPersonInfo where TeaUID=%s))"
            
            self.teaClass = self.sqlhandler.executeQuerySQL(sql, self.UID)

            return True
        return False


if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/",
                                             TeaGetClassListRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
