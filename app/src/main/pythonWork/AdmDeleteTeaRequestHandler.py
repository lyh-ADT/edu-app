import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler


class AdmDeleteTeaRequestHandler(tornado.web.RequestHandler):
    def get(self):
        """
        从数据库删除老师信息
        """
        try:
            self.sqlhandler = None
            self.teaId = self.get_argument("teaId")
            if self.deleteTea():
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

    def deleteTea(self):

        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')
        if self.sqlhandler.getConnection():

            sql = "DELETE FROM TeaPersonInfo where TeaId='{0}'".format(
                self.teaId)

            if self.sqlhandler.executeOtherSQL(sql):
                return True
        return False


if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/",
                                             AdmDeleteTeaRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
