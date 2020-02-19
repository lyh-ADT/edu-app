import SqlHandler
import json
import uuid

def isUIDValid(self):
    self.sqlhandler = SqlHandler.SqlHandler(Host='139.159.176.78',
                                                User='root',
                                                Password='liyuhang8',
                                                DBName='PersonDatabase')
                    
    if self.sqlhandler.getConnection():
        self.UID = self.get_cookie("UID", "no")
        sql = "select teaId from TeaPersonInfo where TeaUID='{0}'".format(self.UID)
        print(sql)
        result = self.sqlhandler.executeQuerySQL(sql)
        print(result, self.request.full_url())
        if len(result) == 1:
            return True
    return False

def parseJsonRequestBody(self):
    self.args = json.loads(self.request.body)

def get36UuidStr():
    return str(uuid.uuid4())