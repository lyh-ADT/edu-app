import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler
import utils


class AdmDeleteStuRequestHandler(tornado.web.RequestHandler):
    def get(self):
        """
        从数据库删除学生信息
        """
        try:
            self.sqlhandler = None
            if "UID" not in self.request.cookies:
                self.write("error")
                return

            if not utils.isUIDValid(self):
                self.write("no uid")
                return
            self.stuId = self.get_argument("StuId")
            if self.deleteStu():
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

    def deleteStu(self):

        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')
        if self.sqlhandler.getConnection():

            sql = "DELETE FROM StuPersonInfo where StuId='{0}'".format(
                self.stuId)

            if self.sqlhandler.executeOtherSQL(sql):
                return True
        return False


if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/",
                                             AdmDeleteStuRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
