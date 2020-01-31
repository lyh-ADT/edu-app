package com.edu_app.controller;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.edu_app.R;
import com.edu_app.model.Login;
import com.edu_app.view.LoginAndRegisterFragment;

public class RegisterController {
    public RegisterController(Fragment fragment, View view){
        bindListener(fragment, view);
    }

    private void bindListener(final Fragment fragment, final View view){
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
                // TODO: 添加具体的注册逻辑
                if(userPassword.equals(userRepeatPassword)){
                    Login.sendLoginRequest(userName_tv.getText().toString(), userPassword);
                }
            }
        });
    }
}
