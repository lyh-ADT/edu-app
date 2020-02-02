package com.edu_app.controller;


import java.io.File;

public class MainController {
    private String username;
    public String uid;
    public MainController(String username){
        this.username=username;
    }
    public Boolean existUidFile(){
//        判断本地是否有这个文件
        File file = new File("uid");
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }
    public void saveUid(){
//        从服务器获取uid并保存到本地

    }
    public void setUid(String uid){
        this.uid = uid;
    }

    public boolean uidIsRight(){
//        if(uid过期){
//            return false;
//        }

        return true;
    }
}

