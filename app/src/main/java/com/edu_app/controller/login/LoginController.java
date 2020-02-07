package com.edu_app.controller.login;

import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.edu_app.R;
import com.edu_app.model.login.Login;
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

                Login login = new Login(LoginController.this);
                login.sendRequest(userName_tv.getText().toString(), userPassword_tv.getText().toString());
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
    }

    public void loginSuccess(String uid){
        // TODO：添加具体的登录成功后的逻辑
        fragment.requireActivity().finish();
    }

    public void loginFail(String reason){
        Looper.prepare();
        Toast.makeText(fragment.getContext(), reason, Toast.LENGTH_LONG).show();
        Looper.loop();
    }
}
