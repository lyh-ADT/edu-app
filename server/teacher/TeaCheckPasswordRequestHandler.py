import tornado.ioloop
import tornado.web
import tornado.httpclient
import json
import sys
sys.path.append("..")
import SqlHandler


class TeaCheckPasswordRequestHandler(tornado.web.RequestHandler):
    def post(self):
        """
        从数据库获取学生信息返回给客户端

        """
        try:
            print("收到检查密码信息的请求")
            self.sqlhandler = None
            body = json.loads(self.request.body.decode('utf8'))
            self.teaUid = body["teaUid"]
            self.teaPassword = body["teaPassword"]
            print(self.teaUid)
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
            sql = """select * from TeaPersonInfo where TeaUid=%s and TeaPassword=%s"""
            rs = self.sqlhandler.executeQuerySQL(sql, self.teaUid,
                                                 self.teaPassword)
            print(rs)
            if len(rs) == 1:
                return True
        return False
