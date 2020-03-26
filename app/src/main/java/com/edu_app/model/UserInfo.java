package com.edu_app.model;



import java.io.Serializable;

// 用户类
public class UserInfo extends Model implements Serializable {
    //  姓名，年龄，性别，职业，住址，联系电话，qq号
    private String sex;
    private String name;
    private int age;
    private String job;
    private String address;
    private String phonenumber;
    private String qqnumber;

    public UserInfo() {
        this.name = "张三";
        this.age = 0;
        this.address = null;
        this.job = null;
        this.sex = null;
        this.phonenumber = null;
        this.qqnumber = null;
    }

    public UserInfo(String name, int age, String sex, String job, String address, String phonenumber, String qqnumber) {
        this.name = name;
        this.age=age;
        this.sex=sex;
        this.job=job;
        this.address=address;
        this.phonenumber=phonenumber;
        this.qqnumber=qqnumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getQqnumber() {
        return qqnumber;
    }

    public void setQqnumber(String qqnumber) {
        this.qqnumber = qqnumber;
    }
}
