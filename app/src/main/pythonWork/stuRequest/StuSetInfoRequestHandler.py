import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler
import json


class StuSetInfoRequestHandler(tornado.web.RequestHandler):
    def post(self):
        """
        获取修改的数据，写入到数据库
        不允许修改用户名，不允许修改班级
        """
        try:
            print("收到修改个人信息请求")
            body = json.loads(self.request.body)
            self.sqlhandler = None

            self.stuUid = body["stuUid"]
            self.stuPassword = body["stuPassword"]
            self.stuName = body["stuName"]

            self.stuSex = body["stuSex"]
            self.stuAge = body["stuAge"]
            self.stuPhoneNumber = body["stuPhoneNumber"]
            self.stuQQ = body["stuQQ"]
            self.stuAddress = body["stuAddress"]

            if self.SetStuInfo():

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

    def SetStuInfo(self):
        """
        给学生设置个人信息
        """
        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')
        if self.sqlhandler.getConnection():
            sql = """select * from StuPersonInfo where StuUid='{0}'""".format(
                self.stuUid)
            rs = self.sqlhandler.executeQuerySQL(sql)
            if len(rs) == 1:

                sql = """UPDATE StuPersonInfo SET StuName='{1}',
                StuSex='{2}',
                StuAge='{3}',
                StuPassword='{4}',
                StuPhoneNumber='{5}',
                StuQQ='{6}',
                StuAddress='{7}' where StuUid='{0}'""".format(
                    self.stuUid, self.stuName, self.stuSex, self.stuAge,
                    self.stuPassword, self.stuPhoneNumber, self.stuQQ,
                    self.stuAddress)
              
                if self.sqlhandler.executeOtherSQL(sql):
                    return True

        return False
