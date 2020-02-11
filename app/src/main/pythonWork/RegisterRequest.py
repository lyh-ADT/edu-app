import requests


def request_post():
    try:
        response = requests.post(url="http://localhost:8080",
                                 data={
                                     'stuId': '用户名',
                                     'pwd': '123456',
                                     'stuName': '学生名字'
                                 })
        print("=========RESPONSE.BODY======")
        print(response)
    except Exception as e:
        # Other errors are possible, such as IOError.
        print("Other errors are possible, such as IOError Error: " + str(e))


if __name__ == "__main__":
    request_post()