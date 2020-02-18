import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler
import json


class TeaSetInfoRequestHandler(tornado.web.RequestHandler):
    def post(self):
        """
        获取修改的数据，写入到数据库
        不允许修改用户名，不允许修改班级
        """
        try:
            print("收到修改个人信息请求")
            body = json.loads(self.request.body)
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
        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')
        if self.sqlhandler.getConnection():
            sql = """select * from TeaPersonInfo where TeaUid='{0}'""".format(
                self.teaUid)
            rs = self.sqlhandler.executeQuerySQL(sql)
            if len(rs) == 1:

                sql = """UPDATE TeaPersonInfo SET TeaName='{1}',
                TeaSex='{2}',
                TeaAge='{3}',
                TeaPassword='{4}',
                TeaPhoneNumber='{5}',
                TeaQQ='{6}',
                TeaAddress='{7}' where TeaUid='{0}'""".format(
                    self.teaUid, self.teaName, self.teaSex, self.teaAge,
                    self.teaPassword, self.teaPhoneNumber, self.teaQQ,
                    self.teaAddress)
              
                if self.sqlhandler.executeOtherSQL(sql):
                    return True

        return False
