package com.xuhao.javaBean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;

public class FriendRelation extends BmobObject{

    
    public String getFriendname() {
        return friendname;
    }
    public void setFriendname(String friendname) {
        this.friendname = friendname;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    private String username;
    private String friendname;
}
