package com.edu_app.model.teacher.practice;

import androidx.core.util.Pair;

import com.edu_app.controller.teacher.Controller;
import com.edu_app.model.NetworkUtility;
import com.edu_app.model.teacher.Model;
import com.edu_app.model.teacher.TeacherInfo;

import java.io.IOException;
import java.util.List;

public class Judge implements Model {
    public TeacherInfo getTeacherInfo() {
        return teacherInfo;
    }

    private TeacherInfo teacherInfo;

    public Judge(TeacherInfo teacherInfo){
        this.teacherInfo = teacherInfo;
    }

    @Override
    public List<Pair<Integer, Object>> getShowField() {
        return null;
    }

    @Override
    public void setController(Controller controller) {

    }

    public String sendJudgement(String practiceId, String studentId, int[] scores) throws IOException {

        return NetworkUtility.postRequest(teacherInfo.getHost()+"/judge_practice", teacherInfo.getUID(),
                new Bean(studentId, practiceId, scores));
    }

    private class Bean{
        private String stuId;
        private String practiceId;
        private int stuScore;
        private String scoreDetail;

        public Bean(String studentId, String practiceId, int[] scores) {
            this.stuId = studentId;
            this.practiceId = practiceId;
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            for(int i=0; i < scores.length; ++i){
                sb.append(i+1).append(":").append(scores[i]).append(",");
                stuScore+=scores[i];
            }
            sb.deleteCharAt(sb.length()-1);
            sb.append("}");
            this.scoreDetail = sb.toString();
        }
    }
}
