import tornado.ioloop
import tornado.web
import tornado.httpclient
import json
import sys
sys.path.append("..")
import SqlHandler


class StuCheckPasswordRequestHandler(tornado.web.RequestHandler):
    def post(self):
        """
        从数据库获取学生信息返回给客户端

        """
        try:
            print("收到检查密码信息的请求")
            self.sqlhandler = None
            body = json.loads(str(self.request.body,encoding="utf-8"))
            self.stuUid = body["stuUid"]
            self.stuPassword = body["stuPassword"]
            print(self.stuUid)
            if self.checkPassword():

                self.write({"success": True})
                self.finish()
            else:
                raise RuntimeError
        except Exception as e:
            print(e)
            self.write({"success": False})
            self.finish()
        finally:
            if self.sqlhandler is not None:
                self.sqlhandler.closeMySql()

    def checkPassword(self):
        """
        从数据库读取学生信息
        """
        self.sqlhandler = SqlHandler.SqlHandler()
        if self.sqlhandler.getConnection():
            """
            查询该用户的课程信息
            """
            sql = """select * from StuPersonInfo where StuUid=%s and StuPassword=%s"""
            rs = self.sqlhandler.executeQuerySQL(sql, self.stuUid,
                                                 self.stuPassword)
            print(rs)
            if len(rs) == 1:
                return True
        return False
