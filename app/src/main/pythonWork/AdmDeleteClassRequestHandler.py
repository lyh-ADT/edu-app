import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler


class AdmDeleteClassRequestHandler(tornado.web.RequestHandler):
    def get(self):
        """
        从数据库删除班级信息

        """
        try:
            self.sqlhandler = None
            self.classId = self.get_argument("classId")
            if self.deleteClass():
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

    def deleteClass(self):

        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')
        if self.sqlhandler.getConnection():

            sql = "DELETE FROM CLASS where ClassId='{0}'".format(self.classId)

            if self.sqlhandler.executeOtherSQL(sql):
                return True
        return False


if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/",
                                             AdmDeleteClassRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
