import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler


class AdmGetTeaListRequestHandler(tornado.web.RequestHandler):
    def get(self):
        """
        从数据库获取老师列表返回给管理员
        """
        try:
            self.sqlhandler = None
            if self.getTeaList():
                self.write(self.teaList)
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

    def getTeaList(self):
        """
        从数据库读取老师列表
        """
        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')
        if self.sqlhandler.getConnection():

            sql = "select TeaId,TeaName from TeaPersonInfo"

            self.teaList = self.sqlhandler.executeQuerySQL(sql)

            return True
        return False


if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/",
                                             AdmGetTeaListRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
