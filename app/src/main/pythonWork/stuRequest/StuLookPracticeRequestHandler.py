import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler
import json


class StuLookPracticeRequestHandler(tornado.web.RequestHandler):
    def post(self):
        """
        从数据库获取学生练习题返回给客户端

        """
        try:
            print("收到查看试题详情的请求")
            body = json.loads(self.request.body)
            self.sqlhandler = None
            self.stuUid = body["stuUid"]
            self.practiceId = body["practiceId"]
            if self.getPractice():
                self.write({
                    "success": True,
                    "data": {
                        "examDetail": self.examDetail,
                        "teaAnswer": self.teaAnswer,
                        "fullScore": self.fullScore,
                        "scoreDetail": self.scoreDetail,
                        "stuAnswer": self.stuAnswer,
                        "stuScore": self.stuScore
                    }
                })
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
            获取具体习题和老师答案,学生答案,学生成绩
            """
            sql = """select StuId from StuPersonInfo where StuUid='{0}'""".format(
                self.stuUid)
            rs = self.sqlhandler.executeQuerySQL(sql)
            print(rs)
            if len(rs) == 1:
                self.stuId = rs[0]["StuId"]
                sql = "select ExamDetail,TeaAnswer,FullScore from PRACTICE where PracticeId='{0}'".format(
                    self.practiceId)
                rs = self.sqlhandler.executeQuerySQL(sql)
                print(rs)
                self.examDetail = rs[0]['ExamDetail']
                self.teaAnswer = rs[0]['TeaAnswer']
                self.fullScore = rs[0]['FullScore']

                sql = "select ScoreDetail,StuScore,StuAnswer from SCORE where PracticeId='{0}' and StuId='{1}'".format(
                    self.practiceId, self.stuId)
                rs = self.sqlhandler.executeQuerySQL(sql)
                print(rs)
                self.scoreDetail = rs[0]['ScoreDetail']
                self.stuAnswer = rs[0]['StuAnswer']
                self.stuScore = rs[0]['StuScore']
            return True
        return False
