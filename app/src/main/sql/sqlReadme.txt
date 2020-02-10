PersonDatabase
    tabale
        StuPersonInfo
            StuId  (学号) 主键 唯一 varchar(11) 
            name (姓名）varchar(30)
            sex (性别) 男女
            birthday (生日) Birthday
            phoneNumber (电话号码) char(11)
            qqNumber (qq号) varchar 
            address(地址)
            age (年龄) 0~100
            class (班级) varchar(100)
            course (正在学习的课程)varchar(100)
            practice (可以查阅和做的习题)
        TeaPersonInfo
            TeaId (工号) 主键 唯一 varchar(11)
            name (姓名）varchar(30)
            sex (性别)男女
            phoneNumber (电话号码)char(11)
            qqNumber (qq号) varchar 
            age (年龄) 0~100
            address(地址)
            class (加入的班级) varchar(100)
            course (管理的课程) varchar(100)
            practice (已经发布和已经批改的作业)
        Score
            PracticeId (试题编号) 联合主键（Practice的外键）
            StuId （学号） 
            stuScore (学生总得分) varchar(3) 小于试卷满分
            scoreDetail (每题得分情况) json字符串 text
            
        Practice
            PracticeId （试题编号）主键   varchar(100)
            fullScore （试卷满分）varchar(3)
            examDetail (每个题目) json字符串 text
        Class
            ClassId （班级编号）
            Teacher 这个班有哪些老师
            Student 这个班有哪些学生
        Course
            CourseId (课程编号)
            class (哪些班有这个课)
            practice (这个课有哪些习题)
            Teacher (这个课由哪个老师负责)
        
            