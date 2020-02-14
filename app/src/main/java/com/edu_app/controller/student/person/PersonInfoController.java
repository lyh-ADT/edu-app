package com.edu_app.controller.student.person;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.edu_app.R;

public class PersonInfoController implements View.OnClickListener {
    private final AppCompatActivity activity;
    private EditText idEditText;
    private Button changeBt;
    private EditText nameEditText;
    private EditText passwordEditText;
    private EditText sexEditText;
    private EditText ageEditText;
    private EditText qqEditText;
    private EditText phoneEditText;
    private EditText addressEditText;

    public PersonInfoController(AppCompatActivity activity) {
        this.activity = activity;
        initView();
    }

    private void initView() {
        idEditText = (EditText) activity.findViewById(R.id.personPage_personInfo_id);
        nameEditText = (EditText) activity.findViewById(R.id.personPage_personInfo_name);
        passwordEditText = (EditText) activity.findViewById(R.id.personPage_personInfo_password);
        sexEditText = (EditText) activity.findViewById(R.id.personPage_personInfo_sex);

        ageEditText = (EditText) activity.findViewById(R.id.personPage_personInfo_age);

        qqEditText = (EditText) activity.findViewById(R.id.personPage_personInfo_qq);

        phoneEditText = (EditText) activity.findViewById(R.id.personPage_personInfo_phone);
        addressEditText = (EditText) activity.findViewById(R.id.personPage_personInfo_address);
        changeBt = (Button) activity.findViewById(R.id.personPage_personInfo_changeBt);
        changeBt.setOnClickListener(this);
        idEditText.setEnabled(false);
        nameEditText.setEnabled(false);
        passwordEditText.setEnabled(false);
        sexEditText.setEnabled(false);
        ageEditText.setEnabled(false);
        qqEditText.setEnabled(false);
        phoneEditText.setEnabled(false);
        addressEditText.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        if(changeBt.getText().toString().equals("修改资料")){
            Log.e("error","---------------");
            nameEditText.setEnabled(true);
            passwordEditText.setEnabled(true);
            sexEditText.setEnabled(true);
            ageEditText.setEnabled(true);
            qqEditText.setEnabled(true);
            phoneEditText.setEnabled(true);
            addressEditText.setEnabled(true);
            changeBt.setText("确认修改");
        }else{
            showDialog();
        }



    }

    private void showDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder((Context) activity);
        dialog.setTitle("修改资料提示");
        dialog.setMessage("确认修改资料吗？");
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeInfo();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void changeInfo() {
//        TODO:上传更新的资料，输入密码正确才能上传
    }

    public void getPersonInfo() {
//        TODO:从服务器获取学生信息填充到相应的文本框
    }


}
