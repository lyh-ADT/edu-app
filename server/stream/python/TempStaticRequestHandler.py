import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler
import uuid
import utils

class TempStaticRequestHandler(tornado.web.RequestHandler):
    def get(self, t):
        path = self.request.path
        if t == "html":
            with open("../html/"+path[path.rindex("/")+1:], "rb") as f:
                self.write(f.read())
                self.finish()
        elif t == "css":
            with open("../css/"+path[path.rindex("/")+1:], "rb") as f:
                self.set_status(200)
                self.set_header("Content-Type", "text/css;")
            
                self.write(f.read())
                self.finish()
        elif t == "js":
            with open("../js/"+path[path.rindex("/")+1:], "rb") as f:
                self.set_header("Content-Type", "application/x-javascript")
                self.write(f.read())
                self.finish()
        
    def post(self):
        """
        添加班级信息
        """
        try:
            self.sqlhandler = None
            if "UID" not in self.request.cookies:
                self.write("error")
                return

            if not utils.isUIDValid(self):
                self.write("no uid")
                return
            
            if self.setClass():
                
                self.write("success")
                self.finish()
            else:
                raise RuntimeError
        except Exception as e:
            print(e)
            self.write("error")
            self.finish()
        finally:
            if self.sqlhandler is not None:
                self.sqlhandler.closeMySql()

    def setClass(self):
        
        return True

    
        

if __name__ == "__main__":

    app = tornado.web.Application(handlers=[(r"/", AdmAddClassRequestHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(8080)
    tornado.ioloop.IOLoop.current().start()
