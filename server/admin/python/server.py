import tornado
from AdmLoginRequestHandler import AdmLoginRequestHandler
from AdmGetClassListRequestHandler import AdmGetClassListRequestHandler
from AdmDeleteClassRequestHandler import AdmDeleteClassRequestHandler
from AdmAddTeaRequestHandler import AdmAddTeaRequestHandler
from AdmDeleteTeaRequestHandler import AdmDeleteTeaRequestHandler
from AdmGetTeaListRequestHandler import AdmGetTeaListRequestHandler
from AdmAddClassRequestHandler import AdmAddClassRequestHandler
from AdmModifyClassRequestHandler import AdmModifyClassRequestHandler
from AdmGetStudentListRequestHandler import AdmGetStudentListRequestHandler
from AdmDeleteStuRequestHandler import AdmDeleteStuRequestHandler
from AdmGetClassStuListRequestHandler import AdmGetClassStuListRequestHandler
from AdmDeleteClassStuRequestHandler import AdmDeleteClassStuRequestHandler
from AdmModifyTeaRequestHandler import AdmModifyTeaRequestHandler

app = tornado.web.Application(handlers=[
    (r"/admin/login", AdmLoginRequestHandler),
    (r"/admin/classtable",AdmGetClassListRequestHandler),
    (r"/admin/deleteclass", AdmDeleteClassRequestHandler),
    (r"/admin/addclass", AdmAddClassRequestHandler),
    (r"/admin/modifyclass", AdmModifyClassRequestHandler),
    (r"/admin/classstudenttable", AdmGetClassStuListRequestHandler),
    (r"/admin/teachertable", AdmGetTeaListRequestHandler),
    (r"/admin/addteacher", AdmAddTeaRequestHandler),
    (r"/admin/deleteteacher", AdmDeleteTeaRequestHandler),
    (r"/admin/modifyteacher", AdmModifyTeaRequestHandler),
    (r"/admin/studenttable", AdmGetStudentListRequestHandler),
    (r"/admin/deletestudent", AdmDeleteStuRequestHandler),
    (r"/admin/kickstudent", AdmDeleteClassStuRequestHandler),
])
http_server = tornado.httpserver.HTTPServer(app)
http_server.listen(7000)
tornado.ioloop.IOLoop.current().start()