package com.edu_app.controller.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.edu_app.R;
import com.edu_app.model.login.Login;
import com.edu_app.view.activityMain;
import com.edu_app.view.login.LoginAndRegisterFragment;

public class LoginController {
    private Fragment fragment;
    public LoginController(Fragment fragment, View view){
        this.fragment = fragment;
        bindListener(view);
    }

    private void bindListener(final View view){
        Button login_btn = view.findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView userName_tv = view.findViewById(R.id.username_edit);
                TextView userPassword_tv = view.findViewById(R.id.password_edit);
                RadioGroup type = view.findViewById(R.id.type_rg);
                int accountType = type.getCheckedRadioButtonId() == R.id.student ? Login.LoginParam.FLAG_STUDENT : Login.LoginParam.FLAG_TEACHER;

                Login login = new Login(LoginController.this);
                login.sendRequest(userName_tv.getText().toString(), userPassword_tv.getText().toString(), accountType);
            }
        });

        Button register_btn = view.findViewById(R.id.register_btn);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = fragment.requireFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.login_activity, LoginAndRegisterFragment.newInstance("register")).commit();
            }
        });

        RadioGroup type = view.findViewById(R.id.type_rg);
        type.check(R.id.student);
    }

    public void loginSuccess(String uid, int accountType){
        //写文件
        Context context = fragment.getContext();
        SharedPreferences pref = context.getSharedPreferences("uid", Context.MODE_PRIVATE);
        pref.edit().putString("uid", uid).apply();
        String type = accountType == Login.LoginParam.FLAG_STUDENT ? "student" : "teacher";
        pref.edit().putString("accountType", type).apply();
        Intent intent = new Intent(context, activityMain.class);
        context.startActivity(intent);

        fragment.requireActivity().finish();
    }

    public void loginFail(String reason){
        Looper.prepare();
        Toast.makeText(fragment.getContext(), reason, Toast.LENGTH_LONG).show();
        Looper.loop();
    }
}
