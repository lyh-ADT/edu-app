import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler
import json
import uuid

class StuRegisterRequestHandler(tornado.web.RequestHandler):
    def post(self):
        try:
            self.sqlhandler = None
            self.args = json.loads(self.request.body)

            if self.register():
                self.write("success")
                self.finish()
            else:
                raise RuntimeError
        except Exception as e:
            print(e)
            self.write("账户名重复")
            self.finish()
        finally:
            if self.sqlhandler is not None:
                self.sqlhandler.closeMySql()

    def register(self):

        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')
        if self.sqlhandler.getConnection():
            """
            查询该老师的信息 id+pwd12
            """

            sql = "insert into StuPersonInfo(StuId, StuPassword, stuName, StuSex, StuAge, StuPhoneNumber, StuQQ, StuAddress) values('{}', '{}', '{}', '{}', '{}', '{}', '{}', '{}');".format(
                                        self.args["userId"], self.args["password"], self.args["userName"], self.args["sex"], self.args["age"], self.args["phone"], self.args["qq"], self.args["address"])
            
            print(sql)
            return self.sqlhandler.executeOtherSQL(sql)



if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/login", StuRegisterRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()