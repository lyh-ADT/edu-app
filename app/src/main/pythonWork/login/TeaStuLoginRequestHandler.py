import SqlHandler
import uuid
import tornado.web


class TeaStuLoginRequestHandler(tornado.web.RequestHandler):
    def post(self):
        print("接收到登录请求")
        try:
            self.sqlhandler = None
            self.request.body.decode("utf-8")
            self.userId = self.get_body_argument("userId")
            self.userPassword = self.get_body_argument("userPassword")
            print(self.userId, self.userPassword)
            # self.flag = self.get_body_agrument("flag")
            self.flag = 1
            if self.checkInfo():
                
                uid = str(self.userId) + str(uuid.uuid4())
                if self.flag == 0:
                    sql = """UPDATE TeaPersonInfo SET TeaUid='{0}'""".format(
                        uid)
                elif self.flag == 1:
                    sql = """UPDATE StuPersonInfo SET StuUid='{0}'""".format(
                        uid)
                if self.sqlhandler.executeOtherSQL(sql):
                    self.set_status(200)
                    self.write(uid)
                    self.finish()
                else:
                    raise RuntimeError
            else:
                raise RuntimeError
        except Exception as e:
            print(e)
            self.set_status(201)

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
            """
            查询该老师的信息 id+pwd flag=0
            """
            """
            查询该学生的信息 id+pwd flag=1
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
