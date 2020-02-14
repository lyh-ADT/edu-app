import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler


class StuSetClassRequestHandler(tornado.web.RequestHandler):
    def get(self):
        """
        获取班级邀请码，写入到数据库

        """
        try:
            self.sqlhandler = None
            self.stuId = self.get_argument("stuId")
            self.stuClass = self.get_argument("stuClass")

            if self.SetStuClass():
                self.write("success")
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

    def SetStuClass(self):
        """
        给学生设置班级邀请码
        """
        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')
        if self.sqlhandler.getConnection():
            sql = """select StuClass from StuPersonInfo where StuId='{0}'""".format(
                self.stuId)
            stuclass = str(self.sqlhandler.executeQuerySQL(sql)[0]["StuClass"])
            if len(stuclass) != 0:
                sql = """UPDATE StuPersonInfo SET StuClass='{0}'""".format(
                    stuclass + "," + self.stuClass)
            else:
                sql = """UPDATE StuPersonInfo SET StuClass='{0}'""".format(
                    self.stuClass)
            print(sql)
            if self.sqlhandler.executeOtherSQL(sql):
                return True
        return False


if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/", StuSetClassRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
