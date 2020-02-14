import requests


def request_post():
    try:
        response = requests.post(url="http://localhost:8080",
                                 data={
                                     "StuId": "李四id",
                                     "StuName": "张三Name",
                                     "StuSex": '女',
                                     "StuAge": '18',
                                     "StuPassword": "48855",
                                     "StuPhoneNumber": '12365478945',
                                     "StuQQ": '884856266',
                                     "StuAddress": '四川'
                                 })
        print("=========RESPONSE.BODY======")
        print(response)
    except Exception as e:
        # Other errors are possible, such as IOError.
        print("Other errors are possible, such as IOError Error: " + str(e))


if __name__ == "__main__":
    request_post()