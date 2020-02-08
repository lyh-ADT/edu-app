package com.edu_app.controller.teacher.practice;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.widget.Toast;

import com.edu_app.R;
import com.edu_app.controller.teacher.Controller;
import com.edu_app.controller.teacher.question.QuestionInfoController;
import com.edu_app.model.NetworkUtility;
import com.edu_app.model.teacher.practice.PictureQuestionItem;
import com.edu_app.model.teacher.TeacherInfo;
import com.edu_app.model.teacher.practice.PracticeItem;
import com.edu_app.model.teacher.practice.QuestionItem;
import com.edu_app.view.teacher.Fragment;

import java.io.IOException;

public class PracticeInfoController extends Controller {
    private final int SELECT_PIC = 0;
    private android.app.Fragment fragment;
    private PracticeItem model;
    private ListAdapter practiceListAdapter;
    private Callback callback;
    private TeacherInfo teacherInfo;
    private boolean editable;
    private Dialog dialog;

    public PracticeInfoController(android.app.Fragment fragment, View view, TeacherInfo teacherInfo){
        super(view, new PracticeItem(null));
        this.fragment = fragment;
        model = (PracticeItem)super.model;
        this.teacherInfo = teacherInfo;
        bindListener();
        setFullScreen(fragment.getActivity());
    }

    public interface Callback extends Controller.Callback{
        PracticeItem getPractice();
        boolean editable();
        void show();
    }

    @Override
    public void bindCallback(Controller.Callback callback){
        if(callback == null){
            return;
        }
        this.callback = (Callback)callback;
        model = this.callback.getPractice();
        super.model = model;
        editable = ((Callback) callback).editable();
        setValues();
        bindListener();
    }

    @Override
    protected void bindListener(){
        EditText title_et = view.findViewById(R.id.practice_item_title);
        if(editable){
            title_et.setEnabled(true);
            title_et.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    String t = ((EditText)v).getText().toString();
                    if(t.length() <= 0){
                        model.setTitle(null);
                    }else{
                        model.setTitle(t);
                    }
                    return false;
                }
            });
        } else {
            title_et.setEnabled(false);
        }


        ListView practice_list = view.findViewById(R.id.practice_list);
        practiceListAdapter = new ListAdapter(fragment.getActivity().getLayoutInflater());
        practice_list.setAdapter(practiceListAdapter);

        Button addQuestion_btn = view.findViewById(R.id.add_question_btn);
        if(editable){
            addQuestion_btn.setVisibility(View.VISIBLE);
            addQuestion_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction();
                    Fragment questionFragment = Fragment.newInstance("question_info", teacherInfo, new QuestionInfoController.Callback() {
                        @Override
                        public void addQuestion(QuestionItem questionItem) {
                            questionItem.setOrderNumber(model.getQuestionCount()+1);
                            model.addQuestion(questionItem);
                            practiceListAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void show() {
                            FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction();
                            transaction.show(fragment);
                            transaction.commit();
                        }

                        @Override
                        public boolean editable(){
                            return true;
                        }

                        @Override
                        public QuestionItem getQuestion() {
                            return null;
                        }
                    });
                    transaction.hide(fragment);
                    transaction.add(R.id.main_fragment, questionFragment);
                    transaction.commit();
                }
            });
        } else {
            addQuestion_btn.setVisibility(View.GONE);
        }

        final Button deleteQuestion_btn = view.findViewById(R.id.delete_question);
        if(editable){
            deleteQuestion_btn.setVisibility(View.VISIBLE);
            deleteQuestion_btn.setOnClickListener(new View.OnClickListener() {
                private boolean deleteMode = false;
                @Override
                public void onClick(View v) {
                    if(deleteMode){
                        // 切换回详情
                        deleteMode = false;
                        QuestionController.setDeleteMode(false);
                        deleteQuestion_btn.setText(R.string.delete_question);
                    } else {
                        // 进入删除模式
                        deleteMode = true;
                        QuestionController.setDeleteMode(true);
                        deleteQuestion_btn.setText(R.string.cancel_text);
                    }
                }
            });
        }else{
            deleteQuestion_btn.setVisibility(View.GONE);
        }


        Button addPractice_btn = view.findViewById(R.id.add_practice_btn);
        if(editable){
            addPractice_btn.setVisibility(View.VISIBLE);
            addPractice_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(model.getTitle() == null || model.getQuestionCount() == 0){
                        Toast.makeText(fragment.getActivity().getApplicationContext(), "信息不完整", Toast.LENGTH_LONG).show();
                        return;
                    }
                    final android.app.AlertDialog dialog = showProgressBar(v.getContext(), "上传中...");
                    new Thread(){
                        @Override
                        public void run(){
                            try {
                                String response = NetworkUtility.postRequest(teacherInfo.getHost()+"/add_practice", teacherInfo.getUID(), model);
                                if("success".equals(response)){
                                    unSetFullScreen(fragment.getActivity());
                                    FragmentManager manager = fragment.getFragmentManager();
                                    FragmentTransaction transaction = manager.beginTransaction();
                                    transaction.remove(fragment);
                                    callback.show();
                                    transaction.commit();
                                } else {
                                    Looper.prepare();
                                    Toast.makeText(view.getContext(), response, Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                    Looper.loop();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                Looper.prepare();
                                Toast.makeText(view.getContext(), "网络异常, 添加失败", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                                Looper.loop();
                            }
                        }
                    }.start();
                }
            });
        } else {
            addPractice_btn.setVisibility(View.GONE);
        }


        Button exit_btn = view.findViewById(R.id.exit_btn);
        if(editable){
            exit_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getView().getContext());
                    builder.setMessage("操作不会被保存")
                            .setTitle("确认退出")
                            .setCancelable(true)
                            .setPositiveButton("取消", null)
                            .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    unSetFullScreen(fragment.getActivity());
                                    FragmentManager manager = fragment.getFragmentManager();
                                    FragmentTransaction transaction = manager.beginTransaction();
                                    transaction.remove(fragment);
                                    callback.show();
                                    transaction.commit();
                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        } else {
            exit_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    unSetFullScreen(fragment.getActivity());
                    FragmentManager manager = fragment.getFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.remove(fragment);
                    callback.show();
                    transaction.commit();
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == SELECT_PIC){
            if(data == null){
                Log.e("PracticeInfoController", "onActivityResult获取图片data为空");
                return;
            }
            dialog.dismiss();
            Uri uri = data.getData();
            PictureQuestionItem question = new PictureQuestionItem();
            question.addUri(uri);
            question.setOrderNumber(model.getQuestionCount()+1);
            model.addQuestion(question);
            practiceListAdapter.notifyDataSetChanged();
        }
    }

    private class ListAdapter extends BaseAdapter {
        private LayoutInflater inflater;

        ListAdapter(LayoutInflater inflater){
            this.inflater = inflater;
        }

        @Override
        public int getCount() {
            return model.getQuestionCount();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(position > -1){
                convertView = inflater.inflate(R.layout.item_question, parent, false);
                new QuestionController(convertView, model.getQuestionAt(position), fragment).bindCallback(new QuestionController.Callback() {
                    @Override
                    public void addQuestion(QuestionItem questionItem) {}

                    @Override
                    public void deleteQuestion(QuestionItem questionItem) {
                        model.deleteQuestion(questionItem);
                        ListAdapter.this.notifyDataSetChanged();
                    }
                });
            }
            return convertView;
        }
    }
}
