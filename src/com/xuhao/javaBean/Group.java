package com.xuhao.javaBean;

import java.util.ArrayList;

import android.R.string;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

public class Group extends BmobObject{
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public BmobRelation getTeacher() {
        return teacher;
    }
    public void setTeacher(BmobRelation teacher) {
        this.teacher = teacher;
    }
    public BmobRelation getStudent() {
        return student;
    }
    public void setStudent(BmobRelation student) {
        this.student = student;
    }
    public ArrayList<String> getMessage() {
	return message;
    }
    public void setMessage(ArrayList<String> message) {
	this.message = message;
    }
    private String type;
    private BmobRelation teacher;
    private BmobRelation student;
    private ArrayList<String> message = new ArrayList<String>();

}
