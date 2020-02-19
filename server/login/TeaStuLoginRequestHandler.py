import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler
import json
import uuid

class TeaStuLoginRequestHandler(tornado.web.RequestHandler):
    def post(self):
        try:
            self.sqlhandler = None
            self.args = json.loads(self.request.body)
            self.userId = self.args["userId"]
            self.userPassword = self.args["userPassword"]
            print(self.args)
            self.flag = self.args["flag"]
            if self.checkInfo():
                self.uid = self.userId + str(uuid.uuid4())
                self.updateUID()
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
            if (self.flag == 0):

                sql = "select * from TeaPersonInfo where TeaId='{0}' and TeaPassword='{1}'".format(
                    self.userId, self.userPassword)
            elif (self.flag == 1):
                sql = "select * from StuPersonInfo where StuId='{0}' and StuPassword='{1}'".format(
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
            if (self.flag == 0):

                sql = "update TeaPersonInfo set TeaUid='{0}' where TeaId='{1}'".format(
                    self.uid, self.userId)
            elif (self.flag == 1):
                sql = "update StuPersonInfo set StuUid='{0}' where StuId='{1}'".format(
                    self.uid, self.userId)
            self.sqlhandler.executeOtherSQL(sql)


if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/login", TeaStuLoginRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()