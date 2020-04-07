import tornado.ioloop
import tornado.web
import tornado.httpclient
import json
import utils
import sys
sys.path.append("..")
import SqlHandler


class AdmGetStudentListRequestHandler(tornado.web.RequestHandler):
    def get(self):
        """
        从数据库获取学生列表返回给管理员
        """
        try:
            self.sqlhandler = None
            if "UID" not in self.request.cookies.keys():
                self.write("no uid")
                return

            if not utils.isUIDValid(self):
                self.write("no uid")
                return
            if self.getStuList():
                self.write(json.dumps(self.stuList if self.stuList is not None else {"length":0}))
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

    def getStuList(self):
        """
        从数据库读取学生信息
        """
        self.sqlhandler = SqlHandler.SqlHandler()
        if self.sqlhandler.getConnection():
            """
            查询所有学生
            """

            sql = """select StuId, stuName, StuSex, StuAge, StuPhoneNumber, StuQQ, StuAddress, group_concat(CourseName separator ',') as StuClass from 
                (select StuPersonInfo.StuId, stuName, StuSex, StuAge, StuPhoneNumber, StuQQ, StuAddress, ClassId from StuPersonInfo left join ClassStuRelation on StuPersonInfo.StuId=ClassStuRelation.StuId) as s
                left join CLASS on s.ClassId=CLASS.ClassId
                group by StuId, stuName, StuSex, StuAge, StuPhoneNumber, StuQQ, StuAddress;
            """

            self.stuList = self.sqlhandler.executeQuerySQL(sql)

            return True
        return False


if __name__ == "__main__":

    app = tornado.web.Application(
        handlers=[(r"/", AdmGetStudentListRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
