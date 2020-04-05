package com.edu_app.model.login;

import com.edu_app.controller.login.LoginController;
import com.edu_app.model.MD5;
import com.edu_app.model.NetworkUtility;

import java.io.IOException;

public class Login {
    private LoginController controller;

    public Login(LoginController controller){
        this.controller = controller;
    }

    public void sendRequest(final String userName, final String password, final int flag){
        final String SERVER_HOST = "http://123.57.101.238:8080/login";// TODO: 修改为服务器地址
        new Thread(){
            @Override
            public void run(){

                String md5_password = MD5.md5(password);

                try {
                    Response response = NetworkUtility.postToJson(SERVER_HOST, null, new LoginParam(userName, md5_password, flag), Response.class);
                    if(response.success){
                        controller.loginSuccess(response.data, flag);
                    } else {
                        controller.loginFail(response.data);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    controller.loginFail("网络连接超时");
                }
            }
        }.start();
    }

    public static class LoginParam{
        public static int FLAG_STUDENT = 1;
        public static int FLAG_TEACHER = 0;
        private String userId;
        private String userPassword;
        private int flag;
        public LoginParam(String userId, String userPassword, int flag){
            this.userId = userId;
            this.userPassword = userPassword;
            this.flag = flag;
        }
    }

    private class Response{
        boolean success;
        String data;
    }
}
