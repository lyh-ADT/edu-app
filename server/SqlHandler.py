from JDBCUtils import JDBCUtils


class SqlHandler():
    def __init__(self):
        self.connection = JDBCUtils.getConnection()

    def getConnection(self):
        if self.connection is not None:
            return True
        return False

    def closeMySql(self):
        try:
            self.connection.close()
        except Exception as e:
            print(e)

    def executeQuerySQL(self, sql, *parms):
        """
        执行数据库操作,返回全部数据
        @ parm：
            sql:需要执行的sql语句
        """
        print(sql % parms)
        cursor = self.connection.cursor()
        cursor.execute(sql, parms)
        rs = cursor.fetchall()
        cursor.close()
        return rs

    def executeOtherSQL(self, sql, *parms):
        """
        执行数据库操作,返回操作是否成功
        @ parm：
            sql:需要执行的sql语句
        """

        try:
            cursor = self.connection.cursor()
            cursor.execute(sql, parms)
            self.connection.commit()
            return True
        except Exception as e:
            self.connection.rollback()
            print(e)
            return False
        finally:
            cursor.close()
    def update(self, sql, *parms):
        """
        执行数据库操作,返回影响行数
        @ parm：
            sql:需要执行的sql语句
        """

        try:
            cursor = self.connection.cursor()
            cursor.execute(sql, parms)
            self.connection.commit()
            return cursor.rowcount
        except Exception as e:
            self.connection.rollback()
            print(e)
            return 0
        finally:
            cursor.close()