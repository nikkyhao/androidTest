package com.xuhao.activity;
import java.util.List;

import com.xuhao.javaBean.Group;
import com.xuhao.javaBean.User;

import cn.bmob.v3.Bmob;
import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;


public class MyApplication extends Application{
    private User presentUser = null;
    private List<User> FriendList = null;//存放此用户所有好友信息
    private List<Bitmap> bmp_list = null;//存放用户好友的头像
    private List<Group> groupList = null;//存放所有此用户分组信息
    public boolean isFriendListChanged = true;//表示如果是第一次或是好友列表有改变，才需要从网络上获取好友列表，否则就不需要
    public boolean isGroupListChanged = true;//表示分组列表，理由同上
    
    public List<Bitmap> getBmp_list() {
		return bmp_list;
	}

	public void setBmp_list(List<Bitmap> bmp_list) {
		this.bmp_list = bmp_list;
	}

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
