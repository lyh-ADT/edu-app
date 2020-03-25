import tornado.ioloop
import tornado.web
import tornado.httpclient
import json
import sys
sys.path.append("..")
import SqlHandler

class StuGetVedioListRequestHandler(tornado.web.RequestHandler):
    def post(self):
        """
        获取练习题列表，返回给客户端
        """
        try:
            print("收到获取视频列表的请求")
            body = json.loads(self.request.body)
            self.sqlhandler = None
            self.vediolist = list()
            self.stuUid = body["stuUid"]
            # 科目
            self.course = body["course"]
            if self.getVedioList():
                self.write({"success": True, "data": self.vediolist})
                self.finish()
            else:
                raise RuntimeError
        except Exception as e:
            print(e)
            self.write({"success": False, "data": "获取视频列表失败"})
            self.finish()
        finally:
            if self.sqlhandler is not None:
                self.sqlhandler.closeMySql()

    def getVedioList(self):
        """
        返回学生的习题列表
        """
        self.sqlhandler = SqlHandler.SqlHandler()

        if self.sqlhandler.getConnection():
            """
            查询练习题
            """

            # 看这个学生是否有班级
            sql = "select StuClass,StuId from StuPersonInfo where StuUid=%s"
            rs = self.sqlhandler.executeQuerySQL(sql,self.stuUid)
            if len(rs) == 1:
                sql = "select RecordTitle,RecordUrl from RecordVedio where RecordSort=%s"
                rs = self.sqlhandler.executeQuerySQL(sql,self.course)
                print(rs)
                self.vediolist = list(rs)
            return True
        return False
