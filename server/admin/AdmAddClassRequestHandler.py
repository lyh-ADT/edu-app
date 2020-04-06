import tornado.ioloop
import tornado.web
import tornado.httpclient
import uuid
import utils
import sys
sys.path.append("..")
import SqlHandler


class AdmAddClassRequestHandler(tornado.web.RequestHandler):
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
            self.courseName = self.get_argument("courseName")
            self.teacher = self.get_argument("teacherId")
            self.classStuNumber = self.get_argument("stuCount")
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
            self.classId = str(uuid.uuid4())
            self.inviteCode = self.getInviteCode()
            sql = "INSERT INTO CLASS(ClassId,CourseName,Teacher,StuNumber, InviteCode) VALUES(%s,%s,%s,%s, %s)"
            if self.sqlhandler.executeOtherSQL(sql, self.classId,
                                             0  self.courseName, self.teacher,
                                               self.classStuNumber,
                                               self.inviteCode):
                return True
        return False

    def getInviteCode(self):
        import string, random
        return "".join(random.sample(string.ascii_lowercase + string.digits,
                                     6))


if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/", AdmAddClassRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
