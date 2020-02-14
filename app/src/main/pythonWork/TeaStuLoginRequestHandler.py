import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler


class TeaStuLoginRequestHandler(tornado.web.RequestHandler):
    def post(self):
        try:
            self.sqlhandler = None

            self.userId = self.get_argument("userId")
            self.userPassword = self.get_argument("userPassword")

            self.flag = self.get_body_agrument("flag")
            if self.checkInfo():
                self.write("success")
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

    def checkInfo(self):

        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')
        if self.sqlhandler.getConnection():
            """
            查询该老师的信息 id+pwd
            """
            if (self.flag == 0):

                sql = "select * from TeaPersonInfo where TeaId='{0}' and TeaPassword='{1}'".format(
                    self.userId, self.userPassword)
            elif (self.flag == 1):
                sql = "select * from StuPersonInfo where StuId='{0}' and StuPassword='{1}'".format(
                    self.userId, self.userPassword)

            if len(self.sqlhandler.executeQuerySQL(sql)) == 1:

                return True
        return False


if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/", TeaStuLoginRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
