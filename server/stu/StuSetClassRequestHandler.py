import tornado.ioloop
import tornado.web
import tornado.httpclient
import json
import sys
sys.path.append("..")
import SqlHandler


class StuSetClassRequestHandler(tornado.web.RequestHandler):
    def post(self):
        """
        获取班级邀请码，写入到数据库

        """
        try:
            print("收到修改班级请求")
            body = json.loads(str(self.request.body,encoding="utf-8"))
            self.sqlhandler = None
            self.stuUid = body["stuUid"]
            self.inviteCode = body["inviteCode"]

            if self.SetStuClass():
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

    def SetStuClass(self):
        """
        给学生设置班级邀请码
        """
        self.sqlhandler = SqlHandler.SqlHandler()
        if self.sqlhandler.getConnection():
            sql = """select ClassId from CLASS where InviteCode=%s"""

            rs = self.sqlhandler.executeQuerySQL(sql, self.inviteCode)
            if len(rs) == 1:
                stuClass = rs[0]["ClassId"]
                sql = """select StuId from StuPersonInfo where StuUid=%s"""

                rs = self.sqlhandler.executeQuerySQL(sql, self.stuUid)
                if len(rs) == 1:
                    stuId = rs[0]["StuId"]
                    sql = """select * from ClassStuRelation where StuId=%s and ClassId=%s"""
                    print(sql)
                    rs = self.sqlhandler.executeQuerySQL(
                        sql, stuId, stuClass)
                    if len(rs) > 0:
                        return True
                    sql = """insert into ClassStuRelation(ClassId,StuId) values(%s,%s)"""
                    print(sql)
                    updateStudent = self.sqlhandler.executeOtherSQL(
                        sql, stuClass,stuId)
                    if updateStudent:
                        return True
        return False
