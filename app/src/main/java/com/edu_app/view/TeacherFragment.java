package com.edu_app.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeacherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeacherFragment extends Fragment {
    private static final String FRAGMENT_TYPE = "fragment_type";

    private String fragmentType;

    public TeacherFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param fragmentType Parameter 1.
     * @return A new instance of fragment TeacherFragment.
     */
    public static TeacherFragment newInstance(String fragmentType) {
        TeacherFragment fragment = new TeacherFragment();
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
        TextView textView = new TextView(getActivity());
        return textView;
    }
}
