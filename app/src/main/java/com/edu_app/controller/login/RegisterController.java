package com.edu_app.controller.login;

import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.edu_app.R;
import com.edu_app.model.MD5;
import com.edu_app.model.NetworkUtility;
import com.edu_app.model.login.Register;
import com.edu_app.view.login.LoginAndRegisterFragment;

import java.io.IOException;

public class RegisterController {
    private final String HOST = "http://123.57.101.238:8080";
    private Fragment fragment;
    private View view;
    private boolean formValid = false;
    private boolean idRepeated = true;

    public RegisterController(Fragment fragment, View view){
        this.fragment = fragment;
        this.view = view;
        bindListener(view);
    }

    private void bindListener(final View view){
        RadioGroup sex_rg = view.findViewById(R.id.user_sex);
        sex_rg.check(R.id.male); // 默认为男性

        Button login_btn = view.findViewById(R.id.back_to_login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = fragment.requireFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.login_activity, LoginAndRegisterFragment.newInstance("login")).commit();
            }
        });

        Button register_btn = view.findViewById(R.id.register_btn);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register register = new Register(RegisterController.this);
                Register.Form form = getForm();
                if(form == null){
                    return;
                }
                register.post(form);
            }
        });
    }

    public void registerSuccess(){
        Looper.prepare();
        Toast.makeText(fragment.getContext(), "注册成功", Toast.LENGTH_LONG).show();
        // 跳转回登录界面让用户登录
        FragmentManager fragmentManager = fragment.requireFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.login_activity, LoginAndRegisterFragment.newInstance("login")).commit();
        Looper.loop();
    }

    public void registerFail(String reason){
        Looper.prepare();
        Toast.makeText(fragment.getContext(), reason, Toast.LENGTH_LONG).show();
        Looper.loop();
    }

    private Register.Form getForm(){
        Register.Form form = new Register.Form();
        form.userId = getCheckedTextViewContent(R.id.user_id);
        if(form.userId == null){
            return null;
        }
        checkRepeatId(form.userId);
        form.userName = getCheckedTextViewContent(R.id.user_name);
        if(form.userName == null){
            return null;
        }

        RadioGroup sex_rg = view.findViewById(R.id.user_sex);
        form.sex = sex_rg.getCheckedRadioButtonId() == R.id.male ? "男" : "女";

        form.age = getCheckedTextViewContent(R.id.user_age);
        if(form.age == null){
            return null;
        }

        form.birthday = getCheckedTextViewContent(R.id.user_birthday);
        if(form.birthday == null){
            return null;
        }

        form.phone = getCheckedTextViewContent(R.id.user_phone_number);
        if(form.phone == null){
            return null;
        }

        form.qq = getCheckedTextViewContent(R.id.user_qq);
        if(form.qq == null){
            return null;
        }

        form.address = getCheckedTextViewContent(R.id.user_address);
        if(form.address == null){
            return null;
        }

        String password = getCheckedTextViewContent(R.id.password_edit);
        String repeatedPassword = getCheckedTextViewContent(R.id.repeat_password_edit);
        if(password == null || repeatedPassword == null){
            return null;
        }
        if(!password.equals(repeatedPassword)){
            setInvalid(R.id.password_edit);
            Toast.makeText(view.getContext(), "两次密码不相同", Toast.LENGTH_LONG).show();
            return null;
        }
        password = MD5.md5(password);
        form.password = password;
        return form;
    }

    private String getCheckedTextViewContent(int id){
        String s =  ((EditText)view.findViewById(id)).getText().toString();
        if(s.isEmpty()){
            setInvalid(id);
            return null;
        }
        return s;
    }

    private void setInvalid(int id){
        View v = view.findViewById(id);
        final Drawable oldBack = v.getBackground();
        v.setBackgroundColor(fragment.getResources().getColor(android.R.color.holo_red_light));
        v.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                v.setBackground(oldBack);
            }
        });
        ((ScrollView)view).smoothScrollTo(0, v.getBottom());
        Toast.makeText(view.getContext(), "选项为必填项", Toast.LENGTH_SHORT).show();
    }

    private void checkRepeatId(final String id){
        new Thread(){
            @Override
            public void run(){
                try {
                    boolean repeat = Boolean.parseBoolean(NetworkUtility.postRequest(HOST+"/register", "", "id="+id));
                    idRepeated = repeat;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
