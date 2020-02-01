package com.edu_app.controller;

import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.edu_app.R;
import com.edu_app.model.Register;
import com.edu_app.view.LoginAndRegisterFragment;

public class RegisterController {
    private Fragment fragment;
    public RegisterController(Fragment fragment, View view){
        this.fragment = fragment;
        bindListener(view);
    }

    private void bindListener(final View view){
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
                TextView userName_tv = view.findViewById(R.id.username_edit);
                TextView userPassword_tv = view.findViewById(R.id.password_edit);
                TextView userRepeatPassword_tv = view.findViewById(R.id.repeat_password_edit);

                String userPassword = userPassword_tv.getText().toString();
                String userRepeatPassword = userRepeatPassword_tv.getText().toString();

                if(userPassword.equals(userRepeatPassword)){
                    Register register = new Register(RegisterController.this);
                    register.sendRequest(userName_tv.getText().toString(), userPassword);
                }else{
                    Toast.makeText(view.getContext(), "两次密码不相同", Toast.LENGTH_LONG).show();
                }
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
}
