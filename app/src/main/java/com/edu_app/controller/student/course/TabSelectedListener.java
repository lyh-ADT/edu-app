package com.edu_app.controller.student.course;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edu_app.R;
import com.edu_app.model.NetworkUtility;
import com.edu_app.model.student.VedioInfo;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TabSelectedListener implements TabLayout.OnTabSelectedListener {
    private AppCompatActivity activity;
    private Boolean getSuccess=false;
    private JSONArray data;
    private ListView listView=null;

    public TabSelectedListener(AppCompatActivity activity){
        this.activity = activity;
    }
    
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getData(tab.getText().toString());
            }
        }).start();

        try {
            while (!getSuccess){
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(getSuccess && listView!=null){
            updateView(listView);
        }else {
            Toast.makeText(activity,"获取失败超时",Toast.LENGTH_LONG);
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    private void getData(String course) {

        Intent intent = activity.getIntent();
        String uuid = intent.getExtras().get("uid").toString();
        
        String courseName=null;
        switch (course){
            case "数学课程":
                listView= (ListView) activity.findViewById(R.id.practicePage_practice_math_listview);
                courseName = "math";
                break;
            case "语文课程":
                listView= (ListView) activity.findViewById(R.id.practicePage_practice_chinese_listview);
                courseName = "chinese";
                break;
                case "英语课程":
                listView= (ListView) activity.findViewById(R.id.practicePage_practice_english_listview);
                    courseName = "english";
                    break;
                case "其他课程":
                listView= (ListView) activity.findViewById(R.id.practicePage_practice_other_listview);
                    courseName = "other";
                    break;
        }
        try {

            String body = String.format("{\"stuUid\":\"%s\",\"course\":\"%s\"}", uuid,courseName);
            Log.e("error",body);

            String response = NetworkUtility.postRequest("http://139.159.176.78:8081/stuGetVedioList", body);
            JSONObject jsonObject = JSONObject.parseObject(response);
            getSuccess = jsonObject.getBoolean("success");
            data = jsonObject.getJSONArray("data");
            Log.e("error",data.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    private void updateView(ListView listView) {
        List<String> titles = new ArrayList<String>();
        List<String> urls= new ArrayList<String>();

        for(int i=0,len=data.size();i<len;i++){
            VedioInfo obj = data.getObject(i, VedioInfo.class);
            titles.add(obj.getRecordTitle());
            urls.add(obj.getRecordUrl());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,android.R.layout.simple_list_item_1,titles);//新建并配置ArrayAapeter
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent openVideo = new Intent(Intent.ACTION_VIEW);
                openVideo.setDataAndType(Uri.parse(urls.get(i)), "*/*");
                activity.startActivity(openVideo);
            }
        });
    }
}
