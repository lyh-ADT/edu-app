package com.edu_app.view.teacher.person;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.edu_app.R;
import com.edu_app.controller.teacher.person.PersonMainController;

public class fragmentPerson extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_teacher_person, container, false);
    }
// TODO:监听点击选项按钮，实现个人功能页面与其中子选项的页面跳转

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PersonMainController mainController = new PersonMainController(getActivity());
//        TextView textView = (TextView) getActivity().findViewById(R.id.personPage_bt_login);
        RelativeLayout relativeLayout_1 = getActivity().findViewById(R.id.personPage_stuInfo);
        RelativeLayout relativeLayout_4 =  getActivity().findViewById(R.id.personPage_contactCS);
//        textView.setOnClickListener(mainController);
        relativeLayout_1.setOnClickListener(mainController);
        relativeLayout_4.setOnClickListener(mainController);

    }


}
