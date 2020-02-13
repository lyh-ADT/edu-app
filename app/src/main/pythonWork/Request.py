import requests


def request_get():
    try:
        response = requests.get(url="http://localhost:8080",
                                params={
                                    "teaId": "01"
                                    # "stuClass":"3"
                                    # "practiceId": "p2"
                                })
        print("=========RESPONSE.BODY======")
        print(response)
    except Exception as e:
        # Other errors are possible, such as IOError.
        print("Other errors are possible, such as IOError Error: " + str(e))


if __name__ == "__main__":
    request_get()