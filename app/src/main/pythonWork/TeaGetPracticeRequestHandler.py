import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler


class TeaGetPracticeRequestHandler(tornado.web.RequestHandler):
    def get(self):
        """
        获取练习题列表，返回给老师客户端
        """
        try:
            self.sqlhandler = None
            self.practice = list()
            self.classId = self.get_argument("classId")
            self.practiceId = self.get_body_argument("practiceId")
            if self.getStuPractice():
                print(self.practicelist)
                self.write({self.practicelist})
                self.finish()
            else:
                raise RuntimeError
        except Exception:
            self.write("error")
            self.finish()
        finally:
            if self.sqlhandler is not None:
                self.sqlhandler.closeMySql()
            tornado.ioloop.IOLoop.current().stop()

    def getStuPractice(self):
        """
        返回班级本次所有学生习题列表
        """
        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')

        if self.sqlhandler.getConnection():
            """
            查询练习题
            """
            # 获取班级学生

            sql = "select Student from CLASS where ClassId='" + self.classId + "'"
            stuIdList = str(
                self.sqlhandler.executeQuerySQL(sql)[0]['Student']).split(',')


            for stuId in stuIdList:
                # 判断该学生的某次习题是否被改过
                sql = """select * from SCORE where PracticeId='{0}' and StuId='{1}'""".format(
                    self.practiceId, stuId)
                print(sql)
                if len(self.sqlhandler.executeQuerySQL(sql)) == 1:
                    isDone = True
                else:
                    isDone = False
                sql = """select StuName from StuPersonInfo where StuId='{0}''""".format(stuId)
                stuName = self.sqlhandler.executeQuerySQL(sql)[0]['StuName']
                self.practicelist.append({
                    "stuId" : stuId,
                    "stuName":stuName,
                    "isDone": isDone
                })
            return True
        return False


if __name__ == "__main__":

    app = tornado.web.Application(
        handlers=[(r"/", TeaGetPracticeRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
