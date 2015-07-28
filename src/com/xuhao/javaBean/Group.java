package com.xuhao.javaBean;

import java.util.ArrayList;

import android.R.string;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

public class Group extends BmobObject{
  
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    

}
