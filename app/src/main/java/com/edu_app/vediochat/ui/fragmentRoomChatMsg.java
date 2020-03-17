package com.edu_app.vediochat.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.edu_app.R;
import com.edu_app.vediochat.WebRTCManager;

public class fragmentRoomChatMsg extends Fragment {
    private final ChatSingleActivity activity;

    public fragmentRoomChatMsg(ChatSingleActivity activity){
            this.activity = activity;
        }
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_stu_course_roomchatmsg,container,false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
        }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button btnSubmit = (Button) getActivity().findViewById(R.id.coursePage_roomChat_btnMsg);

        btnSubmit.setOnClickListener(activity);
        EditText ed = (EditText)getActivity().findViewById(R.id.coursePage_roomChat_etMsg);
        ed.addTextChangedListener(activity);

        Log.e("error","加载完毕");
    }
}
