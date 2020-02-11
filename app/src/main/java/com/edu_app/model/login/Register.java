package com.edu_app.model.login;

import com.edu_app.controller.login.RegisterController;
import com.edu_app.model.NetworkUtility;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Register {
    private RegisterController controller;

    public Register(RegisterController controller){
        this.controller = controller;
    }


    public void post(final Form form){
        final String Host = "http://192.168.123.22:2000";
        new Thread(){
            @Override
            public void run(){
                try {
                    String response = NetworkUtility.postRequest(Host+"/register", "", form);
                    if("success".equals(response)){
                        controller.registerSuccess();
                    }else{
                        controller.registerFail(response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    controller.registerFail("网络连接超时");
                }
            }
        }.start();
    }

    public static class Form{
        public String userId;
        public String userName;
        public String sex;
        public String age;
        public String birthday;
        public String phone;
        public String qq;
        public String address;
        public String password;
    }
}
