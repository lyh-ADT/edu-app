package com.edu_app.controller.student.person;

import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;
import com.edu_app.R;
import com.edu_app.model.MD5;
import com.edu_app.model.NetworkUtility;
import com.edu_app.controller.student.Controller;
import java.io.IOException;

public class PersonInfoController extends Controller implements View.OnClickListener {
    private final AppCompatActivity activity;
    private final String uid;
    private EditText idEditText;
    private Button changeBt;
    private EditText nameEditText;
    private EditText oldPasswordEditText;
    private EditText sexEditText;
    private EditText ageEditText;
    private EditText qqEditText;
    private EditText phoneEditText;
    private EditText addressEditText;
    private Boolean setSuccess;
    private EditText newPasswordEditText;
    private LinearLayout oldPasswordLayout;

    private LinearLayout newPasswordLayout;
    private JSONObject data;
    private Boolean getSuccess;
    private Boolean passwordVaild;

    public PersonInfoController(AppCompatActivity activity) {
        this.activity = activity;
        this.uid = this.activity.getIntent().getExtras().get("uid").toString();
        initView();
        initContent();
    }

    private void initContent() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getPersonInfo();
            }
        }).start();
        while (this.getSuccess==null){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(!getSuccess){
            showGetErrorDialog();
        }


    }

    private void initView() {
        oldPasswordLayout = this.activity.findViewById(R.id.personPage_personInfo_oldPasswordLayout);
        newPasswordLayout = this.activity.findViewById(R.id.personPage_personInfo_newPasswordLayout);

        idEditText = activity.findViewById(R.id.personPage_personInfo_id);
        nameEditText = activity.findViewById(R.id.personPage_personInfo_name);
        sexEditText = activity.findViewById(R.id.personPage_personInfo_sex);

        ageEditText = activity.findViewById(R.id.personPage_personInfo_age);

        qqEditText = activity.findViewById(R.id.personPage_personInfo_qq);

        phoneEditText = activity.findViewById(R.id.personPage_personInfo_phone);
        addressEditText = activity.findViewById(R.id.personPage_personInfo_address);
        newPasswordEditText = activity.findViewById(R.id.personPage_personInfo_newPassword);
        oldPasswordEditText = activity.findViewById(R.id.personPage_personInfo_oldPassword);
        changeBt = activity.findViewById(R.id.personPage_personInfo_changeBt);
        changeBt.setOnClickListener(this);

        idEditText.setEnabled(false);
        nameEditText.setEnabled(false);

        sexEditText.setEnabled(false);
        ageEditText.setEnabled(false);
        qqEditText.setEnabled(false);
        phoneEditText.setEnabled(false);
        addressEditText.setEnabled(false);
        changeBt.setText("修改资料");
        oldPasswordLayout.setVisibility(View.GONE);
        newPasswordLayout.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (changeBt.getText().toString().equals("修改资料")) {
            oldPasswordLayout.setVisibility(View.VISIBLE);
            newPasswordLayout.setVisibility(View.VISIBLE);
            nameEditText.setEnabled(true);
            sexEditText.setEnabled(true);
            ageEditText.setEnabled(true);
            qqEditText.setEnabled(true);
            phoneEditText.setEnabled(true);
            addressEditText.setEnabled(true);
            changeBt.setText("确认修改");

        } else {
            showDialog();
        }


    }
    private void showGetErrorDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this.activity);
        dialog.setTitle("获取个人信息提示");
        dialog.setMessage("获取个人信息失败");
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    private void showSetSuccessDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this.activity);
        dialog.setTitle("设置个人信息提示");
        if (this.setSuccess.equals(true)) {
            dialog.setMessage("设置个人信息成功");
        } else {
            dialog.setMessage("设置个人信息失败");
        }
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle("修改资料提示");
        dialog.setMessage("确认修改资料吗？");
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        checkPassword();
                    }
                }).start();
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

        while (passwordVaild==null){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (this.passwordVaild) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                setInfo();
            }
        }).start();
        while (this.setSuccess==null){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        showSetSuccessDialog();
        initView();
        initContent();
            }

        else {
            showPasswordErrorDialog();
        }
    }
    private  void setInfo() {
        String newPassword = this.newPasswordEditText.getText().toString();
        newPassword = MD5.md5(newPassword);
        String userName = this.nameEditText.getText().toString();
        String userSex = this.sexEditText.getText().toString();

        String userAge = this.ageEditText.getText().toString();
        String userAddress = this.addressEditText.getText().toString();
        String userQQ = this.qqEditText.getText().toString();
        String userPhone = this.phoneEditText.getText().toString();

        String body = String.format("{\"stuUid\":\"%s\",\"stuAge\":\"%s\",\"stuSex\":\"%s\",\"stuName\":\"%s\",\"stuPassword\":\"%s\",\"stuQQ\":\"%s\",\"stuPhoneNumber\":\"%s\",\"stuAddress\":\"%s\"}", this.uid, userAge, userSex, userName, newPassword, userQQ, userPhone,userAddress);
        Log.e("error", body);
        String response = null;
        try {
            response = NetworkUtility.postRequest("http://139.159.176.78:8081/stuSetInfo", body);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = JSONObject.parseObject(response);
        this.setSuccess = jsonObject.getBoolean("success");
    }
    private void showPasswordErrorDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle("原始密码错误提示");
        dialog.setMessage("原始密码错误，请重新输入");
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void checkPassword() {

        try {
            String oldPassword = this.oldPasswordEditText.getText().toString();
            String body = "{\"stuUid\":\"" + this.uid + "\",\"stuPassword\":\"" + oldPassword + "\"}";
            Log.e("error", body);
            String response = NetworkUtility.postRequest("http://139.159.176.78:8081/stuCheckPassword", body);
            JSONObject jsonObject = JSONObject.parseObject(response);
            this.passwordVaild = jsonObject.getBoolean("success");
            Log.e("error",this.passwordVaild.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void getPersonInfo() {
//        TODO:从服务器获取学生信息填充到相应的文本框


        try {
            String body = String.format("{\"stuUid\":\"%s\"}", this.uid);
            Log.e("error", body);
            String response = NetworkUtility.postRequest("http://139.159.176.78:8081/stuGetInfo", body);
            JSONObject jsonObject = JSONObject.parseObject(response);
            this.getSuccess = jsonObject.getBoolean("success");
            if(getSuccess){
                this.data = jsonObject.getJSONObject("data");
                String userName = data.getString("StuName");
                String userSex = data.getString("StuSex");

                String userAge = data.getString("StuAge");
                String userAddress = data.getString("StuAddress");
                String userQQ = data.getString("StuQQ");
                String userPhone = data.getString("StuPhoneNumber");
                String userId = data.getString("StuId");
                Log.e("error",data.toString());
                this.idEditText.setText(userId);
                this.nameEditText.setText(userName);
                this.ageEditText.setText(userAge);
                this.addressEditText.setText(userAddress);
                this.sexEditText.setText(userSex);
                this.phoneEditText.setText(userPhone);
                this.qqEditText.setText(userQQ);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
