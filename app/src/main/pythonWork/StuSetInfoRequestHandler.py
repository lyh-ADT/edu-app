import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler


class StuSetInfoRequestHandler(tornado.web.RequestHandler):
    def post(self):
        """
        获取修改的数据，写入到数据库
        不允许修改用户名，不允许修改班级
        """
        try:
            self.sqlhandler = None
            self.stuId = self.get_body_argument("StuId")
            self.StuPassword = self.get_body_argument("StuPassword")
            self.StuName = self.get_body_argument("StuName")
            print(self.StuName)

            self.StuSex = self.get_body_argument("StuSex")
            self.StuAge = self.get_body_argument("StuAge")
            self.StuPhoneNumber = self.get_body_argument("StuPhoneNumber")
            self.StuQQ = self.get_body_argument("StuQQ")
            self.StuAddress = self.get_body_argument("StuAddress")
            print(self.StuName)
            if self.SetStuInfo():
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

    def SetStuInfo(self):
        """
        给学生设置个人信息
        """
        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')
        if self.sqlhandler.getConnection():
            sql = """UPDATE StuPersonInfo SET StuName='{1}',
            StuSex='{2}',
            StuAge='{3}',
            StuPassword='{4}',
            StuPhoneNumber='{5}',
            StuQQ='{6}',
            StuAddress='{7}' where StuId='{0}'""".format(
                self.stuId, self.StuName, self.StuSex, self.StuAge,
                self.StuPassword, self.StuPhoneNumber, self.StuQQ,
                self.StuAddress)
            print(sql)
            if self.sqlhandler.executeOtherSQL(sql):
                return True
        return False


if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/", StuSetInfoRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
