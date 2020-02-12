import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler


class StuPracticeListRequestHandler(tornado.web.RequestHandler):
    def get(self):
        """
        获取练习题列表，返回给客户端
        """
        try:
            self.sqlhandler =None
            self.practiceId = list()
            self.stuId = self.get_argument("stuId")
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
            self.practicelist = []
            # 获取班级id
            sql = "select StuClass from StuPersonInfo where StuId='" + self.stuId + "'"
            stuClassId = str(
                self.sqlhandler.executeQuerySQL(sql)[0]['StuClass']).split(",")
            # print(stuClassId)
            for clsId in stuClassId:
                # 获取习题id
                sql = "select Practice from CLASS where ClassId='" + clsId + "'"

                stuPracticeId = str(
                    self.sqlhandler.executeQuerySQL(sql)[0]['Practice']).split(
                        ',')
                print(stuPracticeId)
                for practiceId in stuPracticeId:
                    # 判断该习题是否被做过
                    sql = """select * from SCORE where PracticeId='{0}' and StuId='{1}'""".format(
                        practiceId, self.stuId)
                    print(sql)
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


if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/",
                                             StuPracticeListRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
