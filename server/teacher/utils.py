import json
import uuid
import sys
sys.path.append("..")
import SqlHandler


def isUIDValid(self):
    self.sqlhandler = SqlHandler.SqlHandler()

    if self.sqlhandler.getConnection():
        self.UID = self.get_cookie("UID", "no")
        sql = "select teaId from TeaPersonInfo where TeaUID=%s"
        print(sql)
        result = self.sqlhandler.executeQuerySQL(sql, self.UID)
        print(result, self.request.full_url())
        if len(result) == 1:
            return True
    return False


def parseJsonRequestBody(self):
    self.args = json.loads(self.request.body)


def get36UuidStr():
    return str(uuid.uuid4())