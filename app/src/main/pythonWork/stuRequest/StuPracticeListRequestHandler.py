import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler
import json


class StuPracticeListRequestHandler(tornado.web.RequestHandler):
    def post(self):
        """
        获取练习题列表，返回给客户端
        """
        try:
            print("收到获取练习列表的请求")
            body = json.loads(self.request.body)
            self.sqlhandler = None
            self.practicelist = list()
            self.stuUid = body["stuUid"]
            self.subject = body["subject"]
            if self.getStuPractice():
                self.write({"success": True, "data": self.practicelist})
                self.finish()
            else:
                raise RuntimeError
        except Exception as e:
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
        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')

        if self.sqlhandler.getConnection():
            """
            查询练习题
            """

            # 获取班级id
            sql = "select StuClass,StuId from StuPersonInfo where StuUid='" + self.stuUid + "'"
            rs = self.sqlhandler.executeQuerySQL(sql)
            if len(rs) == 1:
                stuClassId = str(rs[0]['StuClass']).split(",")
                stuId = str(rs[0]['StuId'])
                print(stuClassId)
                for clsId in stuClassId:
                    # 获取习题id
                    sql = "select Practice,CourseName from CLASS where ClassId='" + clsId + "'"
                    rs = self.sqlhandler.executeQuerySQL(sql)
                    if len(rs) == 0:
                        continue
                    stuPracticeId = str(rs[0]['Practice']).split(',')
                    stuCourseName = str(rs[0]['CourseName'])
                    if self.subject not in stuCourseName:
                        continue
                    
                    for practiceId in stuPracticeId:
                        # 判断该习题是否被做过
                        sql = """select * from SCORE where PracticeId='{0}' and StuId='{1}'""".format(
                            practiceId, stuId)
                        if len(self.sqlhandler.executeQuerySQL(sql)) == 1:
                            isDone = True
                        else:
                            isDone = False
                        self.practicelist.append({
                            "practiceId": practiceId,
                            "isDone": isDone
                        })
            return True
        return False
