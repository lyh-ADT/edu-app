import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler
import uuid


class AdmLoginRequestHandler(tornado.web.RequestHandler):
    def post(self):
        try:
            self.sqlhandler = None

            self.userId = self.get_argument("userId")
            self.userPassword = self.get_argument("userPassword")

            if self.checkInfo():
                self.set_status(200)
                uid = str(uuid.uuid4())
                self.saveUid(uid)
                self.set_cookie("UID", uid, expires_days=7)
                self.write("success")
                self.finish()
            else:
                raise RuntimeError
        except Exception as e:
            print(e)
            self.set_status(403)
            self.write("error")
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
            sql = "select * from AdminAccount where AdminId='{0}' and AdminPassword='{1}'".format(
                self.userId, self.userPassword)
            if len(self.sqlhandler.executeQuerySQL(sql)) == 1:
                return True
        return False

    def saveUid(self, uid):
        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')
        if self.sqlhandler.getConnection():
            sql = "update AdminAccount set UID='{0}' WHERE AdminId='{1}';".format(uid, self.userId)
            self.sqlhandler.executeOtherSQL(sql)


if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/", AdmLoginRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
