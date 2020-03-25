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
            body = json.loads(self.request.body)
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
            sql = """select Student,ClassId from CLASS where InviteCode=%s"""

            rs = self.sqlhandler.executeQuerySQL(sql, self.inviteCode)
            if len(rs) == 1:
                self.stuClass = rs[0]["ClassId"]
                if rs[0]['Student'] is not None:
                    self.studentList = eval(str(rs[0]['Student']))
                else:
                    self.studentList = list()
                print(self.studentList)
                sql = """select StuId,StuClass from StuPersonInfo where StuUid=%s"""

                rs = self.sqlhandler.executeQuerySQL(sql, self.stuUid)
                if len(rs) == 1:
                    if rs[0]["StuClass"] is not None:
                        classList = eval(str(rs[0]["StuClass"]))
                    else:
                        classList = list()
                    stuId = rs[0]["StuId"]
                    if self.stuClass in classList:
                        return True
                    classList.append(self.stuClass)
                    print(classList)
                    sql = """UPDATE StuPersonInfo SET StuClass=%s"""

                    updateStudent = self.sqlhandler.executeOtherSQL(
                        sql, classList)
                    sql = """UPDATE CLASS SET Student=%s"""
                    updateClass = self.sqlhandler.executeOtherSQL(
                        sql, self.studentList.append(stuId))
                    if updateStudent and updateClass:
                        return True
        return False
