
class Jsonable:
    def __str__(self):
        return str(self.getDict())

    def __repr__(self):
        return str(self)
    

    def getDict(self):
        d = {}
        for name, value in vars(self).items():
            d[name] = value
        return d

    def getJson(self):
        try:
            return json.dumps(self.getDict())
        except UnboundLocalError:
            import json
            return json.dumps(self.getDict())

class Student(Jsonable):
    def __init__(self, id):
        self.StuId = id
        self.stuName = "学生"+id
        self.StuSex = "男"
        self.StuAge = 19
        self.StuBirthday = "1999-11-22"
        self.StuPhoneNumber = "12345678910"
        self.StuQQ = "2222222222"
        self.StuAddress = "家"
        self.StuClass = id+"班"

class Teacher(Jsonable):
    def __init__(self, id):
        self.TeaId = id,
        self.TeaName = "教师"+id
        self.TeaSex = "男"
        self.TeaAge = 29
        self.TeaBirthday = "1234-12-23"
        self.TeaPhoneNumber = "12345678910"
        self.TeaQQ = "1111111111"
        self.TeaAddress = "家"
        self.TeaClass = "班级" + id

class Class(Jsonable):
    def __init__(self, id):
        self.ClassId = id
        self.CourseName = "课程"+id
        self.Teacher = "教师"+id
        self.Practice = "练习"+id
        self.Student = "学生"+id
        self.StuNumber = 10

studentList = []
teacherList = []
classList = []

for i in range(10):
    orderStr = str(i)
    studentList.append(Student(orderStr).getDict())
    teacherList.append(Teacher(orderStr).getDict())
    classList.append(Class(orderStr).getDict())