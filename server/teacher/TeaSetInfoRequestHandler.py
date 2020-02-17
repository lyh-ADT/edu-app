import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler
import utils


class StuSetInfoRequestHandler(tornado.web.RequestHandler):
    def post(self):
        """
        获取修改的数据，写入到数据库
        不允许修改用户名，不允许修改班级,老师名字
        """
        try:
            self.sqlhandler = None
            utils.isUIDValid(self)
            self.teaId = self.get_body_argument("teaId")
            self.teaPassword = self.get_body_argument("teaPassword")

            self.teaSex = self.get_body_argument("teaSex")
            self.teaAge = self.get_body_argument("teaAge")
            self.teaPhoneNumber = self.get_body_argument("teaPhoneNumber")
            self.teaQQ = self.get_body_argument("teaQQ")
            self.teaAddress = self.get_body_argument("teaAddress")

            if self.SetTeaInfo():
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

    def SetTeaInfo(self):
        """
        给老师设置个人信息
        """
        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')
        if self.sqlhandler.getConnection():
            sql = """UPDATE TeaPersonInfo SET
            TeaSex='{0}',
            TeaAge='{1}',
            TeaPassword='{2}',
            TeaPhoneNumber='{3}',
            TeaQQ='{4}',
            TeaAddress='{5}' where TeaId='{6}'""".format(
                self.teaSex, self.teaAge, self.teaPassword,
                self.teaPhoneNumber, self.teaQQ, self.teaAddress, self.teaId)
            # print(sql)
            if self.sqlhandler.executeOtherSQL(sql):
                return True
        return False


if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/", StuSetInfoRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
