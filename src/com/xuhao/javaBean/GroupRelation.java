package com.xuhao.javaBean;

import cn.bmob.v3.BmobObject;

public class GroupRelation extends BmobObject{

   
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Group getGroup() {
        return group;
    }
    public void setGroup(Group group) {
        this.group = group;
    }
    private User user;
    private Group group;
}
