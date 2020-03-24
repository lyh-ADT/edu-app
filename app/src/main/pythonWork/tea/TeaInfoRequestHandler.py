import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler


class TeaInfoRequestHandler(tornado.web.RequestHandler):
    def get(self):
        """
        从数据库获取老师信息返回给客户端

        """
        try:
            self.sqlhandler = None
            self.teaId = self.get_argument("teaId")
            if self.getTeaInfo():
                self.write(self.teaInfo)
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
            查询该老师的信息
            """
            # 获取键值对
            sql = "select * from TeaPersonInfo where StuId='" + self.stuId + "'"
            self.TeaInfo = self.sqlhandler.executeQuerySQL(sql)
            return True
        return False


if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/", TeaInfoRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
