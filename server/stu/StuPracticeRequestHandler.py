import tornado.ioloop
import tornado.web
import tornado.httpclient
import json
import sys
sys.path.append("..")
import SqlHandler


class StuPracticeRequestHandler(tornado.web.RequestHandler):
    def post(self):
        """
        从数据库获取学生练习题返回给客户端

        """
        try:
            print("收到获取试题详情的请求")
            body = json.loads(str(self.request.body,encoding="utf-8"))
            self.sqlhandler = None
            self.stuUid = body["stuUid"]
            self.practiceId = body["practiceId"]
            if self.getPractice():

                self.write({"success": True, "data": self.examDetail})
                self.finish()
            else:
                raise RuntimeError
        except Exception as e:
            print(e)
            self.write({"success": False, "data": "获取试题详情失败"})
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
            获取具体习题
            
            """
            sql = """select StuId from StuPersonInfo where StuUid=%s"""

            rs = self.sqlhandler.executeQuerySQL(sql, self.stuUid)
            if len(rs) == 1:
                sql = "select ExamDetail,TeaAnswer,FullScore from PRACTICE where PracticeId=%s"

                rs = self.sqlhandler.executeQuerySQL(sql, self.practiceId)
                self.examDetail = rs[0]['ExamDetail']

            return True
        return False
