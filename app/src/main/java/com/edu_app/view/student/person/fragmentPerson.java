package com.edu_app.view.student.person;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import android.app.Fragment;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edu_app.R;

import com.edu_app.controller.student.person.PersonMainController;

public class fragmentPerson extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_person, container, false);
    }
// TODO:监听点击选项按钮，实现个人功能页面与其中子选项的页面跳转


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PersonMainController mainController = new PersonMainController(this);
//        TextView textView = (TextView) getActivity().findViewById(R.id.personPage_bt_login);
        LinearLayout linearLayout_1 = (LinearLayout) getActivity().findViewById(R.id.personPage_stuInfo);
        LinearLayout linearLayout_2 = (LinearLayout) getActivity().findViewById(R.id.personPage_stuCourse);
        LinearLayout linearLayout_3 = (LinearLayout) getActivity().findViewById(R.id.personPage_stuDownload);
        LinearLayout linearLayout_4 = (LinearLayout) getActivity().findViewById(R.id.personPage_contactCS);
//        textView.setOnClickListener(mainController);
        linearLayout_1.setOnClickListener(mainController);
        linearLayout_2.setOnClickListener(mainController);
        linearLayout_3.setOnClickListener(mainController);
        linearLayout_4.setOnClickListener(mainController);

    }


}
