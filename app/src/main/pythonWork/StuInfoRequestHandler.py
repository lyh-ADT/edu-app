import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler


class StuInfoRequestHandler(tornado.web.RequestHandler):
    def get(self):
        """
        从数据库获取学生信息返回给客户端

        """
        try:
            self.sqlhandler = None
            self.stuId = self.get_argument("stuId")
            if self.getStuInfo():
                self.write(self.stuinfo)
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

    def getStuInfo(self):
        """
        从数据库读取学生信息
        """
        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')
        if self.sqlhandler.getConnection():
            """
            查询该用户的信息
            """
            # 获取键值对
            sql = "select * from StuPersonInfo where StuId='" + self.stuId + "'"
            self.stuinfo = self.sqlhandler.executeQuerySQL(sql)
            return True
        return False


if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/", StuInfoRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
