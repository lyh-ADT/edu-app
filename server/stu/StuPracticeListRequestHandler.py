import tornado.ioloop
import tornado.web
import tornado.httpclient
import json
import sys
sys.path.append("..")
import SqlHandler
import traceback


class StuPracticeListRequestHandler(tornado.web.RequestHandler):
    def post(self):
        """
        获取练习题列表，返回给客户端
        """
        try:
            print("收到获取练习列表的请求")
            body = json.loads(str(self.request.body,encoding="utf-8"))
            self.sqlhandler = None
            self.practicelist = list()
            self.stuUid = body["stuUid"]
            self.subject = body["subject"]
            if self.getStuPractice():
                print(self.practicelist)
                self.write({"success": True, "data": self.practicelist})
                self.finish()
            else:
                raise RuntimeError
        except Exception as e:
            print(traceback.format_exc())
            print(e)
            self.write({"success": False, "data": "获取练习列表失败"})
            self.finish()
        finally:
            if self.sqlhandler is not None:
                self.sqlhandler.closeMySql()

    def getStuPractice(self):
        """
        返回学生的习题列表
        """
        self.sqlhandler = SqlHandler.SqlHandler()

        if self.sqlhandler.getConnection():
            """
            查询练习题
            """

            # 获取班级id
            sql = "select StuId from StuPersonInfo where StuUid=%s"
            rs = self.sqlhandler.executeQuerySQL(sql, self.stuUid)
            if len(rs) == 1:
                stuId = str(rs[0]['StuId'])
                sql = "select ClassId from ClassStuRelation where StuId=%s"
                listClass = self.sqlhandler.executeQuerySQL(sql, stuId)
                if len(rs) >= 1:
                    for rs in listClass:
                        # 获取习题id
                        sql = "select CourseName from CLASS where ClassId=%s"
                        rs2 = self.sqlhandler.executeQuerySQL(sql, rs["ClassId"])
                        if len(rs2) == 0:
                            continue
                        stuCourseName = str(rs2[0]['CourseName'])
                        if self.subject not in stuCourseName:
                            continue
                        sql = "select PracticeId from PRACTICE where ClassId=%s"
                        print(sql)
                        rs3 = self.sqlhandler.executeQuerySQL(sql, rs["ClassId"])
                        print(rs3)
                        for rs4 in rs3:
                            practiceId = rs4["PracticeId"]
                            # 判断该习题是否被做过
                            sql = """select StuAnswer from SCORE where PracticeId=%s and StuId=%s"""

                            rs5 = self.sqlhandler.executeQuerySQL(
                                sql, practiceId, stuId)
                            print(rs5, len(rs5))
                            if len(rs5) == 1 and rs5[0]['StuAnswer'] is not None:
                                isDone = True
                            else:
                                isDone = False
                            self.practicelist.append({
                                "practiceId": practiceId,
                                "isDone": isDone
                            })
                return True
        return False
