import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler
import utils

class AdmAddTeaRequestHandler(tornado.web.RequestHandler):
    def post(self):
        """
        增加老师

        """
        try:
            self.sqlhandler = None
            if "UID" not in self.request.cookies:
                self.write("error")
                return
                
            if not utils.isUIDValid(self):
                self.write("no uid")
                return
            self.teaId = self.get_argument("teaId")
            self.teaName = self.get_argument("teaName")
            self.teaPassword = self.get_argument("teaPassword")
            if self.AddTea():
                self.set_status(200)
                self.write("success")
                self.finish()
            else:
                raise RuntimeError
        except Exception as e:
            print(e)
            self.set_status(403)
            self.write("error")
            self.finish()
        finally:
            if self.sqlhandler is not None:
                self.sqlhandler.closeMySql()

    def AddTea(self):
        """
        将老师信息写入数据库
        """
        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')

        if self.sqlhandler.getConnection():
            sql = "INSERT INTO TeaPersonInfo(TeaId,TeaPassword,TeaName) VALUES('{0}','{1}','{2}')".format(
                self.teaId, self.teaPassword, self.teaName)
            print(sql)
            if self.sqlhandler.executeOtherSQL(sql):
                return True
        return False


if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/", AdmAddTeaRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
