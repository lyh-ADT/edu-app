import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler
import json


class TeaInfoRequestHandler(tornado.web.RequestHandler):
    def post(self):
        """
        从数据库获取教师信息返回给客户端

        """
        try:
            print("收到获取教师信息的请求")
            self.sqlhandler = None

            body = json.loads(self.request.body)          
            self.TeaUid = body["teaUid"]
           
            if self.getTeaInfo():
                self.write({"success": True, "data": self.TeaInfo})
                self.finish()
            else:
                raise RuntimeError
        except Exception as e:
            print(e)
            self.write({"success": False, "data": "获取个人信息失败"})
            self.finish()
        finally:
            if self.sqlhandler is not None:
                self.sqlhandler.closeMySql()

    def getTeaInfo(self):
        """
        从数据库读取教师信息
        """
        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')
        if self.sqlhandler.getConnection():
            """
            查询该用户的信息
            """
            sql = """select TeaId,TeaName,TeaPassword,TeaSex,TeaAge,TeaQQ,TeaPhoneNumber,TeaAddress from TeaPersonInfo where TeaUid='{0}'""".format(
                self.TeaUid)
            rs = self.sqlhandler.executeQuerySQL(
                    sql)
            if len(rs)==1:
                # 获取键值对
                
                self.TeaInfo = rs[0]
                return True
        return False


