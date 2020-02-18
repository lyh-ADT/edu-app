import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler
import utils
import json


class TeaGetPracticeListRequestHandler(tornado.web.RequestHandler):
    def get(self):
        """
        获取练习题列表，返回给老师客户端
        """
        try:
            self.teaClassPractice = []
            self.sqlhandler = None
            if not utils.isUIDValid(self):
                self.write("no uid")
                return
            if self.getTeaClass():
                print(self.teaClassPractice)
                self.write(json.dumps(self.teaClassPractice) if self.teaClassPractice is not None else "[]")
                self.finish()
            else:
                raise RuntimeError
        except Exception as e:
            print(e)
            self.write("error")
            self.finish()
        finally:
            if self.sqlhandler is not None:
                self.sqlhandler.closeMySql()

    def getTeaClass(self):
        """
        返回老师的习题列表
        """
        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')

        if self.sqlhandler.getConnection():
            """
            查询班级列表
            """
            # 获取班级id
            sql = "select * from (select ClassId,CourseName,Practice from CLASS where Teacher=(select TeaId from TeaPersonInfo where TeaUid='"+ self.UID +"')) as a inner join PRACTICE where PracticeId in (a.Practice);"
            classes = self.sqlhandler.executeQuerySQL(sql)
            print(classes)
            for rs in classes:
                courseName = rs["CourseName"]
                practiceId = str(rs["Practice"]).split(",")
                self.teaClassPractice.append({
                        "id":rs["PracticeId"],
                        "classId":rs["ClassId"],
                        "title":rs["PracticeId"],
                        "fullScore":rs["FullScore"],
                        "questions":json.loads(rs["ExamDetail"])
                })
            print(self.teaClassPractice)
            return True
        return False


if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/",
                                             TeaGetPracticeListRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
