import tornado.ioloop
import tornado.web
import tornado.httpclient
import utils
import sys
sys.path.append("..")
import SqlHandler


class AdmModifyTeaRequestHandler(tornado.web.RequestHandler):
    def post(self):
        """
        添加班级信息
        """
        try:
            self.sqlhandler = None
            if "UID" not in self.request.cookies:
                self.write("error")
                return

            if not utils.isUIDValid(self):
                self.write("no uid")
                return
            self.teaId = self.get_argument("TeaId")
            self.teaName = self.get_argument("TeaName")
            self.teaSex = self.get_argument("TeaSex")
            self.teaPhoneNumber = self.get_argument("TeaPhoneNumber")
            self.teaClass = self.get_argument("TeaClass")
            if self.setClass():

                self.write("success")
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

    def setClass(self):
        """
        将班级信息写入数据库
        """
        self.sqlhandler = SqlHandler.SqlHandler()

        if self.sqlhandler.getConnection():
            sql = "UPDATE TeaPersonInfo SET TeaName=%s, TeaSex=%s,TeaPhoneNumber=%s, TeaClass=%s WHERE TeaId=%s;"
            if self.sqlhandler.executeOtherSQL(sql, self.teaName, self.teaSex,
                                               self.teaPhoneNumber,
                                               self.teaClass, self.teaId):
                return True
        return False


if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/",
                                             AdmModifyTeaRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
