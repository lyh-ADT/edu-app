package com.edu_app.controller.student.practice;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edu_app.R;
import com.edu_app.model.Practice;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 控制查看练习题的页面
 */
public class LookExamController{
    private final AppCompatActivity activity;
    private Practice practice;
    private RecyclerView recycler;
    private Button bu_sumbmit;
    private LookExamAdapter adapter;
    /**
     * 服务器返回的数据包括试卷总体信息，以及详细题目，学生的答案，正确答案，学生每题得分
     * {practiceInfo:{练习id:"",本次总分:""...},everyQuestion:{题号:"1",问题:"question",学生答案:"stu_answer",学生分数:"",此题满分:""...}...}
     */
    private Map<String,Map<String, String>> everyQuestionDetail;
    private Map<String,String> practiceInfo;
    public LookExamController(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void setView() {
        activity.setContentView(R.layout.activity_stu_practice_lookexam_recycler);
        everyQuestionDetail = (HashMap)getAllData().get("everyQuestionDetail");
        recycler = (RecyclerView) activity.findViewById(R.id.practicePage_practice_lookexam_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL, false));
        adapter = new LookExamAdapter(activity, everyQuestionDetail);
        recycler.setAdapter(adapter);
//        添加分割线
        recycler.addItemDecoration(new DividerItemDecoration(activity,DividerItemDecoration.VERTICAL));
    }


    public Map getAllData() {
// TODO:从服务器获取返回所需要的所有数据,根据uid和练习id
        Map map = new HashMap();
        Map map1 = new HashMap();
        map1.put("练习id","20200209");
        map1.put("总分","90");
        map.put("practiceInfo",map1);

        Map map2 = new HashMap();
        Map map21 = new HashMap();
        Map map22 = new HashMap();
        Map map23 = new HashMap();
        Map map24 = new HashMap();

        map21.put("question","选择题，这个题哈哈哈");
        map21.put("stuscore","3");
        map21.put("fullscore","5");
        map21.put("stu_answer","学生的选择题答案");
        map21.put("right_answer","标准的答案");
        map2.put("1",map21);

        map22.put("question","填空题，这个题哈哈哈");
        map22.put("stuscore","5");
        map22.put("fullscore","5");
        map22.put("stu_answer","学生的选择题答案");
        map22.put("right_answer","标准的答案");
        map2.put("2",map22);

        map23.put("question","简答题，这个题哈哈哈");
        map23.put("stuscore","5");
        map23.put("fullscore","8");
        map23.put("stu_answer","学生的选择题答案");
        map23.put("right_answer","标准的答案");
        map2.put("3",map23);

        map24.put("question","选择题，这个题哈哈哈");
        map24.put("stuscore","5");
        map24.put("fullscore","5");
        map24.put("stu_answer","学生的选择题答案");
        map24.put("right_answer","标准的答案");
        map2.put("4",map24);
        map.put("everyQuestionDetail",map2);
        return map;
    }

    public void setKeyDown() {
        AlertDialog.Builder dialog = new AlertDialog.Builder((Context) activity);
        dialog.setTitle("退出提示");
        dialog.setMessage("确认退出查看练习吗？");
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finish();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}

