package com.xuhao.javaBean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

public class User extends BmobObject{
    public BmobFile getPortrait() {
        return portrait;
    }
    public void setPortrait(BmobFile portrait) {
        this.portrait = portrait;
    }
    public String getIdentity() {
        return identity;
    }
    public void setIdentity(String identity) {
        this.identity = identity;
    }
    public BmobRelation getFriendRelation() {
        return friendRelation;
    }
    public void setFriendRelation(BmobRelation friendRelation) {
        this.friendRelation = friendRelation;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
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
    public String getUserName() {
        return username;
    }
    public void setUserName(String name) {
        this.username = name;
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
    private String nickName;//默认情况下等于用户名或者是QQ昵称
    private String username;
    private String password;
    private String school;
    private String major;
    private String gender;
    private String identity;
    private String description;
    private BmobFile portrait;
    private BmobRelation friendRelation;
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
