import tornado.ioloop
import tornado.web
import tornado.httpclient
import json
import utils
import traceback
import sys
sys.path.append("..")
import SqlHandler


class AdmChangePasswordRequestHandler(tornado.web.RequestHandler):
    def post(self):
        """
        从数据库获取老师列表返回给管理员
        """
        try:
            self.sqlhandler = None
            if "UID" not in self.request.cookies:
                self.write("error")
                return

            if not utils.isUIDValid(self):
                self.write("no uid")
                return
            self.old_psd = self.get_argument("comfirm")
            self.new_psd = self.get_argument("newpsd")

            if self.changePassword():
                self.write("success")
                self.finish()
            else:
                raise RuntimeError
        except Exception as e:
            print(traceback.format_exc())
            self.write("error")
            self.finish()
        finally:
            if self.sqlhandler is not None:
                self.sqlhandler.closeMySql()

    def changePassword(self):
        """
        从数据库读取老师列表
        """
        self.sqlhandler = SqlHandler.SqlHandler()
        if self.sqlhandler.getConnection():

            sql = "update AdminAccount set AdminPassword=%s where AdminPassword=%s  and UID=%s;"
            print(sql)
            rs = self.sqlhandler.update(sql, self.new_psd, self.old_psd,
                                                                self.get_cookie("UID", "no"))
            print(rs)
            return 1 == rs
        return False


if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/",
                                             AdmGetTeaListRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
