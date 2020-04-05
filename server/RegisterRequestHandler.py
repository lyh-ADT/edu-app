import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler


class RegisterRequestHandler(tornado.web.RequestHandler):
    def post(self):
        """
        获取从学生所填写的注册信息

        """
        try:
            self.sqlhandler = None
            self.stuId = self.get_body_argument("stuId")
            self.pwd = self.get_body_argument("pwd")
            self.stuName = self.get_body_argument("stuName")
            stuInfo = dict(
                zip(['userId', 'pwd', 'stuName'],
                    [self.stuId, self.pwd, self.stuName]))
            print("----------post_response_info-------")
            print(stuInfo)

            if self.checkUserNmaeRepeat():
                self.write("用户名重复")
                self.finish()

            elif self.register():
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

    def get(self):
        pass

    def checkUserNmaeRepeat(self):
        """
        判断用户名是否重复
        """
        self.sqlhandler = SqlHandler.SqlHandler(Host='123.57.101.238',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')
        if self.sqlhandler.getConnection():
            # sql = 'show tables from PersonDatabase'
            """
            查询是否同用户名
            """
            sql = "select * from StuPersonInfo where StuId='" + self.stuId + "'"
            rows = self.sqlhandler.executeQuerySQL(sql)
            if (len(rows) > 1):
                return True

    def register(self):
        """
        插入数据库学生的信息
        """
        sql = """
                INSERT INTO StuPersonInfo(StuId,StuPwd,StuName)
                VALUES ('{0}', '{1}', '{2}')
              """.format(self.stuId, self.pwd, self.stuName)
        if self.sqlhandler.executeOtherSQL(sql):
            return True


if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/", RegisterRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
