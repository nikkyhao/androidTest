package com.xuhao.javaBean;

import cn.bmob.v3.BmobObject;

public class FriendRelation extends BmobObject{

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public User getFriendUser() {
        return friendUser;
    }
    public void setFriendUser(User friendUser) {
        this.friendUser = friendUser;
    }
    private User user;
    private User friendUser;
}
