import tornado.ioloop
import tornado.web
import tornado.httpclient
import json
import utils
import sys
sys.path.append("..")
import SqlHandler
class AdmGetClassStuListRequestHandler(tornado.web.RequestHandler):
    def get(self):
        """
        从数据库获取班级学生信息返回给管理员

        """
        try:
            self.sqlhandler = None
            if "UID" not in self.request.cookies:
                self.write("error")
                return

            if not utils.isUIDValid(self):
                self.write("no uid")
                return
            self.classStu = dict()
            self.classId = self.get_argument("classId")
            if self.getClassStuList():
                self.write(json.dumps(self.classStu))
                self.finish()
            else:
                raise RuntimeError
        except Exception as e:
            print(e.args)
            self.write("error")
            self.finish()
        finally:
            if self.sqlhandler is not None:
                self.sqlhandler.closeMySql()

    def getClassStuList(self):
        """
        从数据库读取班级学生信息
        """
        self.sqlhandler = SqlHandler.SqlHandler()
        if self.sqlhandler.getConnection():
            """
            查询该用户的信息 id+名字
            """
            sql = "select StuPersonInfo.StuId stuName, StuSex, StuAge, StuPhoneNumber, StuQQ, StuAddress from StuPersonInfo right join (select StuId from ClassStuRelation where ClassId=%s) as c on StuPersonInfo.StuId=c.StuId;"

            self.classStu = self.sqlhandler.executeQuerySQL(sql)

            return True
        return False


if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/",
                                             AdmGetClassStuListRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
