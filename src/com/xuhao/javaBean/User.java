package com.xuhao.javaBean;

import cn.bmob.v3.BmobObject;

public class User extends BmobObject{
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getNickname() {
        return nickName;
    }
    public void setNickname(String nickName) {
        this.nickName = nickName;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getQqOpenId() {
        return qqOpenId;
    }
    public void setQqOpenId(String qqOpenId) {
        this.qqOpenId = qqOpenId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSchool() {
        return school;
    }
    public void setSchool(String school) {
        this.school = school;
    }
    public String getMajor() {
        return major;
    }
    public void setMajor(String major) {
        this.major = major;
    }
    public String getSex() {
        return gender;
    }
    public void setSex(String sex) {
        this.gender = sex;
    }
    private String qqOpenId;
    private String nickName;
    private String name;
    private String school;
    private String major;
    private String gender;

}
