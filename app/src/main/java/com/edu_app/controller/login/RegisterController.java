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
import com.edu_app.model.NetworkUtility;
import com.edu_app.model.login.Register;
import com.edu_app.view.login.LoginAndRegisterFragment;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class RegisterController {
    private final String HOST = "http://192.168.123.22:2000";
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
        try {
            final char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f' };// 用来将字节转换成16进制表示的字符
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] tmp = md.digest();// MD5 的计算结果是一个 128 位的长整数，
            // 用字节表示就是 16 个字节
            char[] str = new char[16 * 2];// 每个字节用 16 进制表示的话，使用两个字符， 所以表示成 16
            // 进制需要 32 个字符
            int k = 0;// 表示转换结果中对应的字符位置
            for (int i = 0; i < 16; i++) {// 从第一个字节开始，对 MD5 的每一个字节// 转换成 16
                // 进制字符的转换
                byte byte0 = tmp[i];// 取第 i 个字节
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];// 取字节中高 4 位的数字转换,// >>>
                // 为逻辑右移，将符号位一起右移
                str[k++] = hexDigits[byte0 & 0xf];// 取字节中低 4 位的数字转换
            }
            password = new String(str);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
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
