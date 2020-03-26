package com.edu_app.model.student;

import com.edu_app.model.Model;

public class VedioInfo extends Model {
    private String RecordTitle;
    private String RecordUrl;

    public String getRecordTitle() {
        return RecordTitle;
    }

    public void setRecordTitle(String recordTitle) {
        RecordTitle = recordTitle;
    }

    public String getRecordUrl() {
        return RecordUrl;
    }

    public void setRecordUrl(String recordUrl) {
        RecordUrl = recordUrl;
    }
}
