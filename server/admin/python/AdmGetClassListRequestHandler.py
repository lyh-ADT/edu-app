import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler
import json
import utils


class AdmGetClassListRequestHandler(tornado.web.RequestHandler):
    def get(self):
        """
        从数据库获取班级列表返回给管理员
        """
        try:
            self.sqlhandler = None
            if "UID" not in self.request.cookies.keys():
                self.write("no uid")
                return

            if not utils.isUIDValid(self):
                self.write("no uid")
                return
            if self.getClassList():
                self.write(json.dumps(self.classList if self.classList is not None else {"length":0}))
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

    def getClassList(self):
        """
        从数据库读取班级学生信息
        """
        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')
        if self.sqlhandler.getConnection():
            """
            查询所有班级
            """

            sql = "select ClassId,CourseName,Teacher,StuNumber,InviteCode from CLASS"

            self.classList = self.sqlhandler.executeQuerySQL(sql)

            return True
        return False


if __name__ == "__main__":

    app = tornado.web.Application(
        handlers=[(r"/", AdmGetClassListRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
