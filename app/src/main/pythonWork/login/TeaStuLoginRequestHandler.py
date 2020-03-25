import sys
sys.path.append("..")
import SqlHandler
import uuid
import tornado.web
import json


class TeaStuLoginRequestHandler(tornado.web.RequestHandler):
    def post(self):
        print("接收到登录请求")
        try:
            self.sqlhandler = None
            body = json.loads(self.request.body)
            self.userId = body["userId"]
            self.userPassword = body["userPassword"]
            print(self.userId, self.userPassword)
            # self.flag = self.get_body_agrument("flag")
            self.flag = 1
            if self.checkInfo():

                uid = str(self.userId) + str(uuid.uuid4())
                if self.flag == 0:
                    sql = """UPDATE TeaPersonInfo SET TeaUid=%s where TeaId=%s"""
                elif self.flag == 1:
                    sql = """UPDATE StuPersonInfo SET StuUid=%s where StuId=%s"""
                if self.sqlhandler.executeOtherSQL(sql, uid, self.userId):
                    self.write({"success": True, "data": uid})
                    self.finish()
                else:
                    print("更新uid数据失败")
                    self.write({"success": False, "data": "登录失败"})
                    self.finish()

            else:
                self.write({"success": False, "data": "登录失败"})
                self.finish()
        except Exception as e:
            print(e)
            self.write({"success": False, "data": "登录失败"})
            self.finish()
        finally:
            if self.sqlhandler is not None:
                self.sqlhandler.closeMySql()

    def checkInfo(self):
        self.sqlhandler = SqlHandler.SqlHandler()
        if self.sqlhandler.getConnection():
            """
            查询该老师的信息 id+pwd flag=0
            """
            """
            查询该学生的信息 id+pwd flag=1
            """
            if (self.flag == 0):

                sql = "select * from TeaPersonInfo where TeaId=%s and TeaPassword=%s"

            elif (self.flag == 1):
                sql = "select * from StuPersonInfo where StuId=%s and StuPassword=%s"

            if len(
                    self.sqlhandler.executeQuerySQL(sql, self.userId,
                                                    self.userPassword)) == 1:

                return True
        return False
