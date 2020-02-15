import tornado.ioloop
import tornado.web
import tornado.httpclient
import SqlHandler


class StuTeaRequestHandler(tornado.web.RequestHandler):
    def post(self):
        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')
        # 老师学生登录操作
        if self.path == '/login':
            self.TeaStuLoginPost()
        elif self.path == '/register':
            self.StuRegisterPost()
        elif self.path == '/doExam':
            self.StuDoExamPost()
        elif self.path == '/stuChangeInfo':
            self.StuChangeInfo()

    def get(self):
        self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')
        if self.path == '/stuGetInfo':
            self.StuGetInfoGet()
        elif self.path == '/stuSetClass':
            self.StuSetClassGet()
        elif self.path == '/stuGetClass':
            self.StuGetClassGet()
        elif self.path == '/stuGetPracticeList':
            self.StuGetPracticeListGet()
        elif self.path == '/stuGetPractice':
            self.StuGetPracticeGet()
        elif self.path == '/stuGetScore':
            pass

    def TeaStuLoginPost(self):
        try:
            userId = self.get_argument("userId")
            userPassword = self.get_argument("userPassword")
            flag = self.get_body_agrument("flag")
            if self.TeaStuLoginCheckInfo(userId, userPassword, flag):
                self.write("success")
                self.finish()
            else:
                raise RuntimeError
        except Exception:
            self.write("error")
            self.finish()
        finally:
            self.sqlhandler.closeMySql()
            tornado.ioloop.IOLoop.current().stop()

    def TeaStuLoginCheckInfo(self, userId, userPassword, flag):
        if self.sqlhandler.getConnection():
            """
            查询该老师的信息 id+pwd
            """
            if (flag == 0):

                sql = "select * from TeaPersonInfo where TeaId='{0}' and TeaPassword='{1}'".format(
                    userId, userPassword)
            elif (flag == 1):
                sql = "select * from StuPersonInfo where StuId='{0}' and StuPassword='{1}'".format(
                    userId, userPassword)

            if len(self.sqlhandler.executeQuerySQL(sql)) == 1:

                return True
        return False

    def StuRegisterPost(self):
        """
        获取从学生所填写的注册信息

        """
        try:
            stuId = self.get_body_argument("stuId")
            pwd = self.get_body_argument("pwd")
            stuName = self.get_body_argument("stuName")

            if self.CheckUserNmaeRepeat(stuId):
                self.write("用户名重复")
                self.finish()

            elif self.StuRegister(stuId, pwd, stuName):
                self.write("success")
                self.finish()
            else:
                raise RuntimeError
        except Exception:
            self.write("error")
            self.finish()
        finally:
            self.sqlhandler.closeMySql()
            tornado.ioloop.IOLoop.current().stop()

    def CheckUserNmaeRepeat(self, stuId):
        """
        判断用户名是否重复
        """
        if self.sqlhandler.getConnection():
            """
            查询是否同用户名
            """
            sql = "select * from StuPersonInfo where StuId='" + stuId + "'"
            rows = self.sqlhandler.executeQuerySQL(sql)
            if (len(rows) > 1):
                return True
        return False

    def StuRegister(self, stuId, pwd, stuName):
        """
        插入数据库学生的信息
        """
        if self.sqlhandler.getConnection():
            sql = """
                    INSERT INTO StuPersonInfo(StuId,StuPwd,StuName)
                    VALUES ('{0}', '{1}', '{2}')
                """.format(stuId, pwd, stuName)
            if self.sqlhandler.executeOtherSQL(sql):
                return True
        return False

    def StuChangeInfoPost(self):
        """
        获取修改的数据，写入到数据库
        不允许修改用户名，不允许修改班级
        """
        try:
            stuId = self.get_body_argument("stuId")
            stuPassword = self.get_body_argument("stuPassword")
            stuName = self.get_body_argument("stuName")

            stuSex = self.get_body_argument("stuSex")
            stuAge = self.get_body_argument("stuAge")
            stuPhoneNumber = self.get_body_argument("stuPhoneNumber")
            stuQQ = self.get_body_argument("stuQQ")
            stuAddress = self.get_body_argument("stuAddress")

            if self.StuChangeInfo(stuId, stuName, stuPassword, stuSex, stuAge,
                                  stuPhoneNumber, stuQQ, stuAddress):
                self.write("success")
                self.finish()
            else:
                raise RuntimeError
        except Exception:
            self.write("error")
            self.finish()
        finally:
            self.sqlhandler.closeMySql()
            tornado.ioloop.IOLoop.current().stop()

    def StuChangeInfo(self, stuId, stuName, stuPassword, stuSex, stuAge,
                      stuPhoneNumber, stuQQ, stuAddress):
        """
        给学生设置个人信息
        """
        if self.sqlhandler.getConnection():
            sql = """UPDATE StuPersonInfo SET StuName='{1}',
            StuSex='{2}',
            StuAge='{3}',
            StuPassword='{4}',
            StuPhoneNumber='{5}',
            StuQQ='{6}',
            StuAddress='{7}' where StuId='{0}'""".format(
                stuId, stuName, stuSex, stuAge, stuPassword, stuPhoneNumber,
                stuQQ, stuAddress)
            if self.sqlhandler.executeOtherSQL(sql):
                return True
        return False

    def StuGetInfoGet(self):
        """
        从数据库获取学生信息返回给客户端

        """
        try:
            stuId = self.get_argument("stuId")
            stuInfo = self.StuGetInfo(stuId)
            if stuInfo is not None:
                self.write(stuInfo)
                self.finish()
            else:
                raise RuntimeError
        except Exception:
            self.write("error")
            self.finish()
        finally:
            self.sqlhandler.closeMySql()
            tornado.ioloop.IOLoop.current().stop()

    def StuGetInfo(self, stuId):
        """
        从数据库读取学生信息
        """

        if self.sqlhandler.getConnection():
            """
            查询该用户的信息
            """
            # 获取键值对
            sql = "select * from StuPersonInfo where StuId='" + stuId + "'"
            stuInfo = self.sqlhandler.executeQuerySQL(sql)
            return stuInfo
        return None

    def StuSetClassGet(self):
        """
        获取班级邀请码，写入到数据库

        """
        try:

            stuId = self.get_argument("stuId")
            stuClass = self.get_argument("stuClass")

            if self.StuSetClass(stuId, stuClass):
                self.write("success")
                self.finish()
            else:
                raise RuntimeError
        except Exception:
            self.write("error")
            self.finish()
        finally:
            self.sqlhandler.closeMySql()
            tornado.ioloop.IOLoop.current().stop()

    def StuSetClass(self, stuId, stuClass):
        """
        给学生设置班级邀请码
        """

        if self.sqlhandler.getConnection():
            sql = """select StuClass from StuPersonInfo where StuId='{0}'""".format(
                stuId)
            stuclass = str(self.sqlhandler.executeQuerySQL(sql)[0]["StuClass"])
            if len(stuclass) != 0:
                sql = """UPDATE StuPersonInfo SET StuClass='{0}'""".format(
                    stuclass + "," + stuClass)
            else:
                sql = """UPDATE StuPersonInfo SET StuClass='{0}'""".format(
                    stuClass)
            if self.sqlhandler.executeOtherSQL(sql):
                return True
        return False

    def StuGetClassGet(self):
        """
        从数据库获取学生信息返回给客户端

        """
        try:
            
            stuId = self.get_argument("stuId")
            courses = self.StuGetCourseInfo(stuId)
            if courses is not None:
                self.write({"courses": courses})
                self.finish()
            else:
                raise RuntimeError
        except Exception:
            self.write("error")
            self.finish()
        finally:
            self.sqlhandler.closeMySql()
            tornado.ioloop.IOLoop.current().stop()

    def StuGetCourseInfo(self, stuId):
        """
        从数据库读取学生信息
        """
        
        if self.sqlhandler.getConnection():
            """
            查询该用户的课程信息
            """
           courses = []
            sql = "select StuClass from StuPersonInfo where StuId='" + stuId + "'"
           
            classId = str(self.sqlhandler.executeQuerySQL(sql)[0]["StuClass"]).split(",")
            
            for clsId in classId:
                sql = "select CourseName from CLASS where ClassId='" + clsId + "'"
                courses.append(self.sqlhandler.executeQuerySQL(sql)[0]["CourseName"])
                
           
            return courses
        return None
    def StuGetPracticeListGet(self):
        """
        获取练习题列表，返回给客户端
        """
        try:
            
            stuId = self.get_argument("stuId")
            practicelist = self.StuGetPracticeList(stuId)
            if practicelist is not null:
                self.write(practicelist)
                self.finish()
            else:
                raise RuntimeError
        except Exception:
            self.write("error")
            self.finish()
        finally:
            
            self.sqlhandler.closeMySql()
            tornado.ioloop.IOLoop.current().stop()
    def StuGetPracticeList(self,stuId):
        """
        返回学生的习题列表
        """

        if self.sqlhandler.getConnection():
            """
            查询练习题
            """
            practicelist = []
            # 获取班级id
            sql = "select StuClass from StuPersonInfo where StuId='" + stuId + "'"
            stuClassId = str(
                self.sqlhandler.executeQuerySQL(sql)[0]['StuClass']).split(",")
            # print(stuClassId)
            for clsId in stuClassId:
                # 获取习题id
                sql = "select Practice from CLASS where ClassId='" + clsId + "'"

                stuPracticeId = str(
                    self.sqlhandler.executeQuerySQL(sql)[0]['Practice']).split(
                        ',')
                
                for practiceId in stuPracticeId:
                    # 判断该习题是否被做过
                    sql = """select * from SCORE where PracticeId='{0}' and StuId='{1}'""".format(
                        practiceId, self.stuId)
                    if len(self.sqlhandler.executeQuerySQL(sql)) == 1:
                        isDone = True
                    else:
                        isDone = False
                    practicelist.append({
                        "practiceId": practiceId,
                        "isDone": isDone
                    })
            return practicelist
        return None
    def StuGetPracticeGet():
        """
        获取具体某一套题
        """
        try:
            practiceId = self.get_argument("practiceId")
            examDetail = self.StuGetPractice(practiceId)
            if examDetail is not None:
                
                self.write(examDetail)
                self.finish()
            else:
                raise RuntimeError
        except Exception:
            self.write("error")
            self.finish()
        finally:
            self.sqlhandler.closeMySql()
            tornado.ioloop.IOLoop.current().stop()

    def StuGetPractice(self,stuId,practiceId):
        if self.sqlhandler.getConnection():
            """
            获取具体习题
            """
            sql = "select ExamDetail from PRACTICE where PracticeId='{0}'".format(
                practiceId)
            examDetail = self.sqlhandler.executeQuerySQL(sql)[0]['ExamDetail']
            return examDetail
        return None
    