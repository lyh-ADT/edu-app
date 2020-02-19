import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler
import utils


class AdmDeleteClassStuRequestHandler(tornado.web.RequestHandler):
    def post(self):
        """
        从数据库删除班级学生信息

        """
        try:
            self.sqlhandler = None
            if "UID" not in self.request.cookies.keys():
                self.write("no uid")
                return

            if not utils.isUIDValid(self):
                self.write("no uid")
                return
            self.classId = self.get_argument("classId")
            self.stuId = self.get_argument("stuId")
            if self.deleteClassStu():
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

    def deleteClassStu(self):

        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')
        if self.sqlhandler.getConnection():

            sql = "SELECT Student FROM CLASS where ClassId='{0}'".format(
                self.classId)

            stuIdList = str(
                self.sqlhandler.executeOtherSQL(sql)[0]["Student"]).split(",")
            stuIdList.remove(self.stuId)
            sql = "UPDATE CLASS SET Student='{0}' where ClassId='{1}'".format(
                ",".join(stuIdList), self.classId)
            if self.sqlhandler.executeOtherSQL(sql):
                return True
        return False


if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/",
                                             AdmDeleteClassStuRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
