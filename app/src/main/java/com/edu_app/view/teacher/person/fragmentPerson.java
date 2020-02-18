package com.edu_app.view.teacher.person;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PersonMainController mainController = new PersonMainController(getActivity());
        LinearLayout linearLayout_1 = getActivity().findViewById(R.id.personPage_stuInfo);
//        LinearLayout linearLayout_2 =getActivity().findViewById(R.id.personPage_stuCourse);
        LinearLayout linearLayout_4 =  getActivity().findViewById(R.id.personPage_contactCS);
        linearLayout_1.setOnClickListener(mainController);
//        linearLayout_2.setOnClickListener(mainController);
        linearLayout_4.setOnClickListener(mainController);
    }


}
