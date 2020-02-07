package com.edu_app.controller.teacher.question;

import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.edu_app.R;
import com.edu_app.controller.teacher.Controller;
import com.edu_app.model.teacher.addquestion.FillBlankQuestionItem;
import com.edu_app.model.teacher.practice.QuestionItem;

public class FillBlankQuestionController extends Controller {
    private FillBlankQuestionItem model;
    private boolean editable;

    public FillBlankQuestionController(View view, QuestionItem model, boolean editable){
        super(view, model);
        this.model = (FillBlankQuestionItem)model;
        this.editable = editable;
        bindListener();
    }

    protected void bindListener(){
        ListView blank_list = view.findViewById(R.id.option_list_lv);
        final FillBlankQuestionController.ListAdapter adapter = new FillBlankQuestionController.ListAdapter();
        blank_list.setAdapter(adapter);

        Button addBlank = view.findViewById(R.id.add_blank);
        if(editable){
            addBlank.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final View dialogView = View.inflate(view.getContext(), R.layout.dialog_add_question, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("添加空位")
                            .setCancelable(true)
                            .setView(dialogView)
                            .setPositiveButton("取消", null)
                            .setNegativeButton("添加", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    TextView question_edit = dialogView.findViewById(R.id.input_question_edit);
                                    String question_string = question_edit.getText().toString();
                                    if(question_string.length() <= 0){
                                        Toast.makeText(view.getContext(), "空位不能为空", Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                    model.addBlank(question_string);
                                    adapter.notifyDataSetChanged();
                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        } else {
            addBlank.setVisibility(View.GONE);
        }
    }

    private class ListAdapter extends BaseAdapter {
        private View.OnClickListener removeListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView order_tv = v.findViewById(R.id.order_number_text);
                final String order = order_tv.getText().toString().substring(0, order_tv.getText().length()-1);

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("删除空位")
                        .setCancelable(true)
                        .setMessage("删除空位"+order)
                        .setPositiveButton("取消", null)
                        .setNegativeButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                model.removeBlank(Integer.parseInt(order)-1);
                                ListAdapter.this.notifyDataSetChanged();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        };

        @Override
        public int getCount() {
            return model.blankCount();
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
                convertView = View.inflate(view.getContext(), R.layout.item_teacher_select_question, null);

                ((TextView)convertView.findViewById(R.id.order_number_text)).setText(position+1+":");
                ((TextView)convertView.findViewById(R.id.content)).setText(model.getBlank(position));
                convertView.setOnClickListener(removeListener);
            }
            return convertView;
        }
    }
}
