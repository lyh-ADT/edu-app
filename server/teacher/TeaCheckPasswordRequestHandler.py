import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler
import json


class TeaCheckPasswordRequestHandler(tornado.web.RequestHandler):
    def post(self):
        """
        从数据库获取学生信息返回给客户端

        """
        try:
            print("收到检查密码信息的请求")
            self.sqlhandler = None
            body = json.loads(self.request.body)
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
        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')
        if self.sqlhandler.getConnection():
            """
            查询该用户的课程信息
            """
            sql = """select * from TeaPersonInfo where TeaUid='{0}' and TeaPassword='{1}'""".format(
                self.teaUid, self.teaPassword)
            rs = self.sqlhandler.executeQuerySQL(sql)
            print(rs)
            if len(rs) == 1:
                return True
        return False
