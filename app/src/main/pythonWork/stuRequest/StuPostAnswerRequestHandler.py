import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler


class StuPostAnswerRequestHandler(tornado.web.RequestHandler):
    def post(self):
        """
        从数据库获取学生练习题返回给客户端

        """
        try:
            print("收到提交试题答案的请求")
            body = self.request.body
            self.sqlhandler = None
            self.stuUid = body("stuUid")
            self.practiceId = body("practiceId")
            self.stuAnswer = body("stuAnswer")
            if self.getPractice():

                self.write({"success": True, "data": "获取试题详情成功"})
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
        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')

        if self.sqlhandler.getConnection():
            """
            设置学生答案
            
            """
            sql = """select StuId from StuPersonInfo where StuUid='{0}'""".format(
                self.stuUid)
            rs = self.sqlhandler.executeQuerySQL(sql)
            if len(rs) == 1:
                self.stuId = rs[0]['StuId']
                sql = "update SCORE set StuAnswer='{0}' where StuId='{1}' and PracticeId='{1}'".format(
                    self.stuAnswer, self.stuId, self.practiceId)
                if self.sqlhandler.executeOtherSQL(sql):

                    return True
        return False
