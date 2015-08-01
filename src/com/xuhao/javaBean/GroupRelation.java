package com.xuhao.javaBean;

import cn.bmob.v3.BmobObject;

public class GroupRelation extends BmobObject{

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getGroupId() {
        return groupId;
    }
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    private String userId;
    private String groupId;
}
