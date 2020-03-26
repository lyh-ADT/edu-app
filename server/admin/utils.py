import sys
sys.path.append("../..")
import SqlHandler


def isUIDValid(self):
    self.sqlhandler = SqlHandler.SqlHandler()
    if self.sqlhandler.getConnection():
        sql = "select AdminId from AdminAccount where UID=%s"
        result = self.sqlhandler.executeQuerySQL(sql,
                                                 self.get_cookie("UID", "no"))
        print(result, self.request.full_url())
        if len(result) == 1:
            return True
    return False