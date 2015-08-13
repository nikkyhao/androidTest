package com.xuhao.c_friendlist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;

import com.example.androidtestproject.R;
import com.example.androidtestproject.R.id;
import com.example.androidtestproject.R.layout;
import com.example.androidtestproject.R.menu;
import com.xuhao.application.MyApplication;
import com.xuhao.javaBean.FriendRelation;
import com.xuhao.javaBean.User;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.text.LoginFilter.UsernameFilterGeneric;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddFriendActivity extends Activity {
    private EditText usernameEdit ;
    private Button commitButton;
    private ImageView backButton;
    private MyApplication mApplication ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_add_friend);
	findView();
	init();
    }

    public void findView() {
	usernameEdit =(EditText)findViewById(R.id.xuhao);
	commitButton = (Button)findViewById(R.id.button1);
	backButton = (ImageView)findViewById(R.id.back);
    }
    public void init() {
	commitButton.setOnClickListener(new AddFriendListener());
	mApplication = (MyApplication)getApplication();
    }
    
    class AddFriendListener implements View.OnClickListener{
	String username ;
	@Override
	public void onClick(View v) {
	    username = usernameEdit.getText().toString();
	    String sqlString = "select * from User where username ='"+username+"'";
		BmobQuery<User> query = new BmobQuery<User>();
		query.doSQLQuery(AddFriendActivity.this,sqlString, new sqlListener());
	}
    }

    private class sqlListener extends SQLQueryListener<User> {
	List<User> searchedUser = null;
	List<BmobObject> batch = new ArrayList<BmobObject>();
   	@Override
   	public void done(BmobQueryResult<User> result, BmobException e) {
   	    if(e==null){//查询成功
   		searchedUser = result.getResults();
   		if(searchedUser!=null && searchedUser.size()>0){//查询成功，返回结果不空
   		    addFriendToServer();
   		    mApplication.isFriendListChanged = true;
   		}
   		else {//查询成功，返回结果为空
   		    showToast("没有这个好友");
   		}
   	    }
   	    else {//查询失败，出现异常
   		showToast("错误码:"+e.getErrorCode()+"错误描述"+e.getMessage());
   	    }
   	  
   	    }
	private void addFriendToServer() {
	   
	    FriendRelation relation1 = new FriendRelation();
	    relation1.setFriendname(searchedUser.get(0).getUserName());
	    relation1.setUsername(mApplication.getPresentUser().getUserName());
	    batch.add(relation1);
	    FriendRelation relation2 = new FriendRelation();
	    relation2.setFriendname(mApplication.getPresentUser().getUserName());
	    relation2.setUsername(searchedUser.get(0).getUserName());
	    batch.add(relation2);
	    new BmobObject().insertBatch(AddFriendActivity.this, batch, new SaveListener() {
	    	    @Override
	    	    public void onSuccess() {
	    	    	showToast("添加好友成功");
	    	    }
	    	    @Override
	    	    public void onFailure(int code, String msg) {
	    	    	showToast("添加好友失败："+msg);
	    	    }
	    	});
	}
   	}
    public void showToast(String s){
	Toast toast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
	toast.show();
    }
    public void Back(View source){
	setResult(6);
	AddFriendActivity.this.finish();
    }
}
