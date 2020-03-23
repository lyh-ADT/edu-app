package com.edu_app.vediochat.controller;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class RoomChatFragmentAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments ;
    private ArrayList<String> tab_titles;
    public RoomChatFragmentAdapter(@NonNull FragmentManager fm, ArrayList<Fragment> fragments , ArrayList<String> tab_titles) {
        super(fm);
        this.fragments=fragments;
        this.tab_titles =tab_titles;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
    public CharSequence getPageTitle(int position) {
        return tab_titles.get(position);
    }


}
