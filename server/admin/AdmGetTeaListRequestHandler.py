import tornado.ioloop
import tornado.web
import tornado.httpclient
import json
import utils
import sys
sys.path.append("..")
import SqlHandler

class AdmGetTeaListRequestHandler(tornado.web.RequestHandler):
    def get(self):
        """
        从数据库获取老师列表返回给管理员
        """
        try:
            self.sqlhandler = None
            if "UID" not in self.request.cookies:
                self.write("error")
                return

            if not utils.isUIDValid(self):
                self.write("no uid")
                return
            if self.getTeaList():
                self.write(json.dumps(self.teaList if self.teaList is not None else {"length":0}))
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

    def getTeaList(self):
        """
        从数据库读取老师列表
        """
        self.sqlhandler = SqlHandler.SqlHandler()
        if self.sqlhandler.getConnection():

            sql = "select TeaId,TeaName,TeaSex,TeaPhoneNumber,CLASS.CourseName as TeaClass from TeaPersonInfo, CLASS where TeaPersonInfo.TeaClass=CLASS.ClassId"
            print(sql)
            self.teaList = self.sqlhandler.executeQuerySQL(sql)

            return True
        return False


if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/",
                                             AdmGetTeaListRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
