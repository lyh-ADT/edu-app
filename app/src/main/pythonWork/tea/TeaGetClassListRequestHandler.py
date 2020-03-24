import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler


class TeaGetClassListRequestHandler(tornado.web.RequestHandler):
    def get(self):
        """
        获取练习题列表，返回给老师客户端
        """
        try:
            self.teaClassPractice = dict()
            self.sqlhandler = None

            self.teaId = self.get_argument("teaId")
            print(self.teaId)
            if self.getTeaClass():
                self.write(self.teaClassPractice)
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
            sql = "select TeaClass from TeaPersonInfo where TeaId='" + self.teaId + "'"

            teaClassId = str(
                self.sqlhandler.executeQuerySQL(sql)[0]['TeaClass']).split(",")

            for clsId in teaClassId:
                # 获取班级名称，班级id
                sql = "select CourseName,Practice from CLASS where ClassId='" + clsId + "'"
                rs = self.sqlhandler.executeQuerySQL(sql)
                courseName = rs[0]["CoureseName"]
                practiceId = str(rs[0]["Practice"]).split(",")
                self.teaClassPractice.update({courseName: practiceId})

            return True
        return False


if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/",
                                             TeaGetClassListRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
