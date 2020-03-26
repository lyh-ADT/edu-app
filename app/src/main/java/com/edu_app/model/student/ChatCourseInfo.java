package com.edu_app.model.student;

import com.edu_app.model.Model;

/**
 * 课堂信息
 */
public class ChatCourseInfo extends Model {
    private String teacherName;
    private String courseName;
    private long startTimeStamp;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public long getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(long startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }
}
