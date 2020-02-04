package com.edu_app.controller.student.person;

import android.app.Fragment;
import android.app.FragmentTransaction;

import android.view.View;

import com.edu_app.R;
import com.edu_app.view.student.person.fragmentContactService;
import com.edu_app.view.student.person.fragmentHistoryDownload;
import com.edu_app.view.student.person.fragmentPersonCourse;
import com.edu_app.view.student.person.fragmentPersonInfo;

public class PersonMainController implements View.OnClickListener {
    private Fragment fragment;

    public PersonMainController(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction ft = fragment.getFragmentManager().beginTransaction();
        Fragment f;
        switch (v.getId()) {
            case R.id.personPage_stuInfo:
                f = new fragmentPersonInfo();
                break;
            case R.id.personPage_stuCourse:
                f = new fragmentPersonCourse();
                break;
            case R.id.personPage_stuDownload:
                f = new fragmentHistoryDownload();
                break;
            case R.id.personPage_contactCS:
                f = new fragmentContactService();
                break;
            default:
                f = null;
                break;
        }
        ft.add(R.id.function_activity,f);
        ft.addToBackStack(null);
        ft.commit();

    }
}
