import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler
import json
import utils

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
        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')
        if self.sqlhandler.getConnection():
            """
            查询该用户的信息 id+名字
            """

            sql = "select Student from CLASS where ClassId='{0}'".format(
                self.classId)

            stuIdList = self.sqlhandler.executeQuerySQL(sql)[0]["Student"]
            if stuIdList is None:
                self.classStu = {"length":0}
                return True
            
            sql = "select StuName from StuPersonInfo where StuId in (" + stuIdList + ");"
            print(sql)
            self.classStu = self.sqlhandler.executeQuerySQL(sql)

            return True
        return False


if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/",
                                             AdmGetClassStuListRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
