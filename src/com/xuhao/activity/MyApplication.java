package com.xuhao.activity;
import java.util.List;

import com.xuhao.javaBean.Group;
import com.xuhao.javaBean.User;

import cn.bmob.v3.Bmob;
import android.app.Application;
import android.util.Log;


public class MyApplication extends Application{
    private User presentUser = null;
    private List<User> FriendList = null;
    private List<Group> groupList = null;
    
    @Override
    public void onCreate() {
	super.onCreate();
	Bmob.initialize(this, "6fd393e552ed1d8dc51dbccf1236cc32");//在一个总的application 中进行初始化
    }
    
    public List<Group> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Group> groupList) {
		this.groupList = groupList;
	}

	

    public User getPresentUser() {
        return presentUser;
    }

    public void setPresentUser(User presentUser) {
        this.presentUser = presentUser;
        Log.d("user", "当前用户已保存");
    }

    public List<User> getFriendList() {
        return FriendList;
    }

    public void setFriendList(List<User> friendList) {
        FriendList = friendList;
    }
    

}
