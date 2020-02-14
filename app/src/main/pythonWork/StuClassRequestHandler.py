import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler


class StuClassRequestHandler(tornado.web.RequestHandler):
    def get(self):
        """
        从数据库获取学生信息返回给客户端

        """
        try:
            self.sqlhandler = None
            self.courses = list()
            self.stuId = self.get_argument("stuId")
            if self.getStuCourseInfo():
                self.write({"courses": self.courses})
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

    def getStuCourseInfo(self):
        """
        从数据库读取学生信息
        """
        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')
        if self.sqlhandler.getConnection():
            """
            查询该用户的课程信息
            """
           
            sql = "select StuClass from StuPersonInfo where StuId='" + self.stuId + "'"
           
            classid = str(self.sqlhandler.executeQuerySQL(sql)[0]["StuClass"]).split(",")
            
            for clsid in classid:
                sql = "select CourseName from CLASS where ClassId='" + clsid + "'"
                self.courses.append(self.sqlhandler.executeQuerySQL(sql)[0]["CourseName"])
                
            # print(self.courses)
            return True
        return False


if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/", StuClassRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
