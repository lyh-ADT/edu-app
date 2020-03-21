import tornado.ioloop
import tornado
import tornado.httpclient
import SqlHandler
import json
import uuid
import utils

class RoomListHandler(tornado.web.RequestHandler):
    def get(self):
        try:
            self.sqlhandler = None
            if "UID" not in self.request.cookies:
                self.write("no uid")
                return

            if not utils.isUIDValid(self):
                self.write("no uid")
                return

            self.roomList = None

            if self.getList():
                self.write(json.dumps(self.roomList) if self.roomList is not None else "[]")
                self.finish()
            else:
                raise RuntimeError
        except Exception as e:
            print(e)
            self.write("[]")
            self.finish()
        finally:
            if self.sqlhandler is not None:
                self.sqlhandler.closeMySql()

    def getList(self):

        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')
        if self.sqlhandler.getConnection():


            sql = """select TeaId, TeaName, b.CourseName, RoomNumber from 
                (select a.TeaId, TeaPersonInfo.TeaName, CourseName, RoomNumber from 
                    (select TeaId, CLASS.CourseName, RoomNumber from StreamRoom left join CLASS on StreamRoom.ClassId=CLASS.ClassId) as a 
                left join TeaPersonInfo on a.TeaId=TeaPersonInfo.TeaId) as b;
            """
          
            self.roomList = self.sqlhandler.executeQuerySQL(sql)
            print(self.roomList)
            return True



if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/", RoomListHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()