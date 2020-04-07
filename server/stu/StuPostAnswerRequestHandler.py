import tornado.ioloop
import tornado.web
import tornado.httpclient
import json
import sys
sys.path.append("..")
import SqlHandler

class StuPostAnswerRequestHandler(tornado.web.RequestHandler):
    def post(self):
        """
        从数据库获取学生练习题返回给客户端

        """
        try:
            print("收到提交试题答案的请求")
            self.sqlhandler = None
            body = json.loads(str(self.request.body,encoding="utf-8"))
            print(body)

            self.stuUid = body["stuUid"]
            self.practiceId = body["practiceId"]
            self.stuAnswer = body["stuAnswer"]
            if self.getPractice():

                self.write({"success": True, "data": "提交答案成功"})
                self.finish()
            else:
                raise RuntimeError
        except Exception as e:
            print(e)
            self.write({"success": False, "data": "提交答案失败"})
            self.finish()
        finally:
            if self.sqlhandler is not None:
                self.sqlhandler.closeMySql()

    def getPractice(self):
        """
        从数据库读取学生信息
        """
        self.sqlhandler = SqlHandler.SqlHandler()

        if self.sqlhandler.getConnection():
            """
            设置学生答案
            
            """
            sql = """select StuId from StuPersonInfo where StuUid=%s"""

            rs = self.sqlhandler.executeQuerySQL(sql,self.stuUid)
            print(rs)
            if len(rs) == 1:
                self.stuId = rs[0]['StuId']
                sql = """insert into SCORE (PracticeId,StuId,StuAnswer) values(%s,%s,%s)"""
                if self.sqlhandler.executeOtherSQL(sql,self.practiceId, self.stuId, str(self.stuAnswer)):
                    return True
        return False
