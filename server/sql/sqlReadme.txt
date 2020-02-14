PersonDatabase
    tabale
        StuPersonInfo
            StuId  (学生用户名) 主键 唯一 
	StuPassword
            name (姓名）varchar(30)
            sex (性别) 男女
            birthday (生日) Birthday
            phoneNumber (电话号码) char(11)
            qqNumber (qq号) varchar 
            address(地址)
            age (年龄) 0-20
            class (班级) varchar(100)
            practice (可以查阅和做的习题)
        TeaPersonInfo
            TeaId (工号) 主键 唯一 varchar(4) 机构给老师
	TeaPassword
            name (姓名）varchar(30)
            sex (性别)男女
            phoneNumber (电话号码)char(11)
            qqNumber (qq号) varchar 
            age (年龄) 0~100
            address(地址)
            class (加入的班级) varchar(100)
            practice (已经发布和已经批改的作业)
        Score
            PracticeId (试题编号) 联合主键（Practice的外键）
            StuId （学号） 
            stuScore (学生总得分) varchar(3) >=0
            scoreDetail (每题得分情况) json字符串 text
            
        Practice
            PracticeId （试题编号）主键   varchar(100)
            fullScore （试卷满分）varchar(3)
            examDetail (每个题目) json字符串 text
        Class
            ClassId （班级编号）
	Course (课程名称)
            Teacher 这个班有哪些老师
            Student 这个班有哪些学生
 
        
            