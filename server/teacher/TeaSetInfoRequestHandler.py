import tornado.ioloop
import tornado.web
import tornado.httpclient
import json
import sys
sys.path.append("..")
import SqlHandler

class TeaSetInfoRequestHandler(tornado.web.RequestHandler):
    def post(self):
        """
        获取修改的数据，写入到数据库
        不允许修改用户名，不允许修改班级
        """
        try:
            print("收到修改个人信息请求")
            body = json.loads(self.request.body.decode('utf8'))
            self.sqlhandler = None

            self.teaUid = body["teaUid"]
            self.teaPassword = body["teaPassword"]
            self.teaName = body["teaName"]

            self.teaSex = body["teaSex"]
            self.teaAge = body["teaAge"]
            self.teaPhoneNumber = body["teaPhoneNumber"]
            self.teaQQ = body["teaQQ"]
            self.teaAddress = body["teaAddress"]

            if self.SetTeaInfo():

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

    def SetTeaInfo(self):
        """
        给学生设置个人信息
        """
        self.sqlhandler = SqlHandler.SqlHandler()
        if self.sqlhandler.getConnection():
            sql = """select * from TeaPersonInfo where TeaUid=%s"""
            rs = self.sqlhandler.executeQuerySQL(sql, self.teaUid)
            if len(rs) == 1:

                sql = """UPDATE TeaPersonInfo SET TeaName=%s,
                TeaSex=%s,
                TeaAge=%s,
                TeaPassword=%s,
                TeaPhoneNumber=%s,
                TeaQQ=%s,
                TeaAddress=%s where TeaUid=%s"""

                if self.sqlhandler.executeOtherSQL(sql, self.teaName,
                                                   self.teaSex, self.teaAge,
                                                   self.teaPassword,
                                                   self.teaPhoneNumber,
                                                   self.teaQQ, self.teaAddress,
                                                   self.teaUid):
                    return True

        return False
