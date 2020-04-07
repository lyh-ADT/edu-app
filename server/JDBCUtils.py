import configparser
import pymysql
import traceback

class JDBCUtils():
    # 读取配置
    __parser = configparser.ConfigParser()
    __confi = __parser.read('../db.ini', encoding='utf-8')
    print(__confi)
    __host = __parser.get('database', 'host')
    __port = int(__parser.get('database', 'port'))
    __user = __parser.get('database', 'userName')
    __password = __parser.get('database', 'password')
    __db = __parser.get('database', 'dbName')
    __charset = __parser.get('database', 'charset')

    @classmethod
    def getConnection(cls):
        """
        连接数据库,如果连接成功则返回连接对象
        """
        try:
            connection = pymysql.connect(
                host=cls.__host,
                user=cls.__user,
                passwd=cls.__password,
                db=cls.__db,
                port=cls.__port,
                charset=cls.__charset,
                cursorclass=pymysql.cursors.DictCursor)
            if connection.open:
                return connection
        except Exception as e:
            print(e, traceback.format_exc())
