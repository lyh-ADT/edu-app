import tornado.ioloop
import tornado.web
import tornado.httpclient
import json
import sys
sys.path.append("..")
import SqlHandler

class StuInfoRequestHandler(tornado.web.RequestHandler):
    def post(self):
        """
        从数据库获取学生信息返回给客户端

        """
        try:
            print("收到获取学生信息的请求")
            self.sqlhandler = None

            body = json.loads(str(self.request.body,encoding="utf-8"))
            self.stuUid = body["stuUid"]
           
            if self.getStuInfo():
                self.write({"success": True, "data": self.stuInfo})
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

    def getStuInfo(self):
        """
        从数据库读取学生信息
        """
        self.sqlhandler = SqlHandler.SqlHandler()
        if self.sqlhandler.getConnection():
            """
            查询该用户的信息
            """
            sql = """select StuId,StuName,StuPassword,StuSex,StuAge,StuQQ,StuPhoneNumber,StuAddress from StuPersonInfo where StuUid=%s"""

            rs = self.sqlhandler.executeQuerySQL(
                    sql,self.stuUid)
            if len(rs)==1:
                # 获取键值对
                
                self.stuInfo = rs[0]
                return True
        return False


