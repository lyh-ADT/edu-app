import pymysql


class SqlHandler():
    def __init__(self,
                 Host,
                 User,
                 Password,
                 DBName,
                 Port=3306,
                 Charset='utf8',
                 cursorclass=pymysql.cursors.DictCursor):
        """
        初始化
        @ parm:
            Host： 主机地址
            User： 用户名
            Password： 密码
            DBName：数据库名
        """
        self.db = DBName
        self.host = Host
        self.user = User
        self.pwd = Password
        self.port = Port
        self.charset = Charset
        self.cursorclass = cursorclass
        self.connection = None

    def getConnection(self):
        """
        连接数据库,如果连接成功则返回连接对象
        """
        try:
            self.connection = pymysql.connect(host=self.host,
                                              user=self.user,
                                              passwd=self.pwd,
                                              db=self.db,
                                              port=self.port,
                                              charset=self.charset,
                                              cursorclass=self.cursorclass)
            if self.connection.open:
                return True
        except Exception as e:
            print(e)

    def closeMySql(self):
        """
        关闭数据库
        """
        self.connection.close()

    def executeQuerySQL(self, sql):
        """
        执行数据库操作,返回全部数据
        @ parm：
            sql:需要执行的sql语句
        """
        cursor = self.connection.cursor()
        cursor.execute(sql)
        rs = cursor.fetchall()
        cursor.close()
        return rs

    def executeOtherSQL(self, sql):
        """
        执行数据库操作,返回操作是否成功
        @ parm：
            sql:需要执行的sql语句
        """

        try:
            cursor = self.connection.cursor()
            cursor.execute(sql)
            self.connection.commit()
            return True
        except Exception as e:
            self.connection.rollback()
            print(e)
            return False
        finally:
            cursor.close()
