import tornado.ioloop
import tornado.web
import tornado.httpclient
import sys
sys.path.append("..")
import SqlHandler


class AdmGetClassTeaRequestHandler(tornado.web.RequestHandler):
    def get(self):
        """
        从数据库获取班级老师信息返回给管理员

        """
        try:
            self.sqlhandler = None
            self.classTea = dict()
            self.classId = self.get_argument("classId")
            if self.getClassTea():
                self.write(self.classTea)
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

    def getClassTea(self):
        """
        从数据库读取班级老师信息
        """
        self.sqlhandler = SqlHandler.SqlHandler()
        if self.sqlhandler.getConnection():
            """
            查询该老师的信息 id+名字
            """

            sql = "select Teacher from CLASS where ClassId=%s"
            teaIdList = str(
                self.sqlhandler.executeQuerySQL(
                    sql, self.classId)[0]["Teacher"]).split(",")

            for teaId in teaIdList:
                sql = "select TeaName from TeaPersonInfo where TeaId=%s"
                teaName = self.sqlhandler.executeQuerySQL(sql,
                                                          teaId)[0]["TeaName"]
                self.classStu.update({teaId: teaName})

            return True
        return False


if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/",
                                             AdmGetClassTeaRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
