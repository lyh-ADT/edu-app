package com.edu_app.vediochat.controller;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.edu_app.R;
import com.edu_app.vediochat.ui.ChatSingleActivity;
import com.edu_app.vediochat.ui.fragmentRoomChatMsg;
import com.edu_app.vediochat.ui.fragmentRoomChatUser;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class RoomChatController {
    private final ChatSingleActivity activity;
    private ArrayList<String> tab_titles;
    private ArrayList<Fragment> fragments;

    public RoomChatController(ChatSingleActivity activity){
        this.activity = activity;
    }
    public ArrayList<String> getTabTitle(){
        tab_titles= new ArrayList<String>();
        tab_titles.add(activity.getString(R.string.roomChat_tab_1));
        tab_titles.add(activity.getString(R.string.roomChat_tab_2));
        return tab_titles;
    }
    public ArrayList<Fragment> setAllPageFragment() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new fragmentRoomChatMsg(activity));
        fragments.add(new fragmentRoomChatUser(activity));
        return fragments;
    }


}
