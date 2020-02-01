package com.edu_app.model;

import com.edu_app.controller.LoginController;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Login {
    private LoginController controller;

    public Login(LoginController controller){
        this.controller = controller;
    }

    public void sendRequest(final String userName, final String password){
        new Thread(){
            @Override
            public void run(){
                post(userName, password);
            }
        }.start();
    }

    private void post(String userName, String password){
        final String SERVER_HOST = "http://192.168.123.22:2000/login";// TODO: 修改为服务器地址
        URL url;
        try {
            url = new URL(SERVER_HOST);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        }

        try {
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            String data = "user="+userName+"&password="+password;

            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            connection.setRequestProperty("Content-Length", String.valueOf(data.length()));

            connection.connect();

            OutputStream outputStream = connection.getOutputStream();

            outputStream.write(data.getBytes());

            outputStream.close();

            InputStream inputStream = connection.getInputStream();
            StringBuilder responseBuilder = new StringBuilder();
            byte[] buffer = new byte[1024];
            int len;
            while((len = inputStream.read(buffer, 0, buffer.length)) != -1){
                responseBuilder.append(new String(buffer, 0, len));
            }

            int responseCode = connection.getResponseCode();
            String response = responseBuilder.toString();
            if(responseCode == 200){
                controller.loginSuccess(response);
            }else if(responseCode == 201){
                controller.loginFail(response);
            }

            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
            controller.loginFail("网络连接超时");
        }
    }
}
