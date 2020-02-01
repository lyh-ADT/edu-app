package com.edu_app.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu_app.R;
import com.edu_app.controller.LoginController;
import com.edu_app.controller.RegisterController;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginAndRegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginAndRegisterFragment extends Fragment {
    // the fragment initialization parameters
    private static final String FRAGMENT_TYPE = "fragment_type";
    private String fragmentType;

    public LoginAndRegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param fragmentType 片段的类型.e.g. login 或者 register
     * @return A new instance of fragment LoginAndRegisterFragment.
     */
    public static LoginAndRegisterFragment newInstance(String fragmentType) {
        LoginAndRegisterFragment fragment = new LoginAndRegisterFragment();
        Bundle args = new Bundle();
        args.putString(FRAGMENT_TYPE, fragmentType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fragmentType = getArguments().getString(FRAGMENT_TYPE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if("login".equals(fragmentType)){
            View view = inflater.inflate(R.layout.fragment_login, container, false);
            new LoginController(this, view);
            return view;
        }else if("register".equals(fragmentType)){
            View view = inflater.inflate(R.layout.fragment_register, container, false);
            new RegisterController(this, view);
            return view;
        }
        return null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
