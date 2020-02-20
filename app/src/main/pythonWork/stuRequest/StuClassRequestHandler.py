import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler
import json


class StuClassRequestHandler(tornado.web.RequestHandler):
    def post(self):
        """
        从数据库获取学生信息返回给客户端

        """
        try:
            print("收到获取班级信息的请求")
            self.sqlhandler = None
            body = json.loads(self.request.body)
            self.courses = list()
            self.stuUid = body["stuUid"]
            print(self.stuUid)
            if self.getStuCourseInfo():

                self.write({"success": True, "data": self.courses})
                self.finish()
            else:
                raise RuntimeError
        except Exception as e:
            print(e)
            self.write({"success": False, "data": "获取班级信息失败"})
            self.finish()
        finally:
            if self.sqlhandler is not None:
                self.sqlhandler.closeMySql()

    def getStuCourseInfo(self):
        """
        从数据库读取学生信息
        """
        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')
        if self.sqlhandler.getConnection():
            """
            查询该用户的课程信息
            """
            sql = """select StuClass from StuPersonInfo where StuUid='{0}'""".format(
                self.stuUid)
            rs = self.sqlhandler.executeQuerySQL(sql)

            if len(rs) == 1:
                classid = eval(str(rs[0]["StuClass"]))

                for clsid in classid:
                    sql = "select CourseName from CLASS where ClassId='" + clsid + "'"
                    rs = self.sqlhandler.executeQuerySQL(sql)
                    if len(rs) == 1:
                        self.courses.append(rs[0]["CourseName"])

                    print(self.courses)
                return True
        return False
