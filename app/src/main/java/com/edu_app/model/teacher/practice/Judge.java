package com.edu_app.model.teacher.practice;

import androidx.core.util.Pair;

import com.edu_app.controller.teacher.Controller;
import com.edu_app.model.NetworkUtility;
import com.edu_app.model.teacher.Model;
import com.edu_app.model.teacher.TeacherInfo;

import java.io.IOException;
import java.util.List;

public class Judge implements Model {
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
        private String studentId;
        private String practiceId;
        private int[] scores;

        public Bean(String studentId, String practiceId, int[] scores) {
            this.studentId = studentId;
            this.practiceId = practiceId;
            this.scores = scores;
        }
    }
}
