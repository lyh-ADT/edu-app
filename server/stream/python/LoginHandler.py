import tornado.ioloop
import tornado
import tornado.httpclient
import SqlHandler
import json
import uuid

class LoginHandler(tornado.web.RequestHandler):
    def post(self):
        try:
            self.sqlhandler = None
            self.userId = self.get_argument("userId")
            self.userPassword = self.get_argument("userPassword")

            if self.checkInfo():
                self.uid = self.userId + str(uuid.uuid4())
                self.updateUID()
                self.set_header("Set-Cookie", "UID="+self.uid)
                self.write({
                    "success":True,
                    "data":self.uid
                })
                self.finish()
            else:
                raise RuntimeError
        except Exception as e:
            print(e)
            self.write({
                "success":False,
                "data": "账号信息不正确"
            })
            self.finish()
        finally:
            if self.sqlhandler is not None:
                self.sqlhandler.closeMySql()

    def checkInfo(self):

        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')
        if self.sqlhandler.getConnection():
            """
            查询该老师的信息 id+pwd
            """


            sql = "select * from StreamTeaAccount where TeaId='{0}' and TeaPassword='{1}'".format(
                    self.userId, self.userPassword)
          

            if len(self.sqlhandler.executeQuerySQL(sql)) == 1:
                return True
        return False

    def updateUID(self):
        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')
        if self.sqlhandler.getConnection():
            sql = "update StreamTeaAccount set TeaUid='{0}' where TeaId='{1}'".format(
                self.uid, self.userId)
            self.sqlhandler.executeOtherSQL(sql)


if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/login", LoginHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()