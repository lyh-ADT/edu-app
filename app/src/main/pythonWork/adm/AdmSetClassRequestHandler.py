import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler


class AdmAddTeaRequestHandler(tornado.web.RequestHandler):
    def post(self):
        """
        增加老师

        """
        try:
            self.sqlhandler = None
            self.classId = self.get_argument("classId")
            self.courseName = self.get_argument("courseName")
            self.teacher = self.get_argument("teacher")
            self.classStuNumber = self.get_argument("classStuNumber")
            if self.setClass():

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

    def setClass(self):
        """
        将班级信息写入数据库
        """
        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')

        if self.sqlhandler.getConnection():
            sql = "INSERT INTO CLASS(ClassId,CourseName,Teacher,StuNumber) VALUES('{0}','{1}','{2}','{3}')".format(
                self.classId, self.courseName, self.teacher,
                self.classStuNumber)
            if self.sqlhandler.executeOtherSQL(sql):
                return True
        return False


if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/", AdmAddTeaRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
