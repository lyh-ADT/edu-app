import SqlHandler


def isUIDValid(self):
    self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')
    if self.sqlhandler.getConnection():
        sql = "select TeaId from StreamTeaAccount where TeaUid='{0}'".format(
            self.get_cookie("UID", "no"))
        result = self.sqlhandler.executeQuerySQL(sql)
        print(result, self.request.full_url())
        if len(result) == 1:
            return True
    return False