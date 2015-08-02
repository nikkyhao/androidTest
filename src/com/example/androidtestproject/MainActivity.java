package com.example.androidtestproject;

import java.util.Iterator;
import java.util.List;

import com.xuhao.javaBean.FriendRelation;
import com.xuhao.javaBean.User;
import com.xuhao.javaBean.User;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    List<User> stu_list = null;
    EditText outputText = null;
    EditText inputText = null;
    EditText usernameText = null;
    EditText passwordText = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	Bmob.initialize(this, "6fd393e552ed1d8dc51dbccf1236cc32");
	//获得组件
	inputText = (EditText)findViewById(R.id.inputText);
	outputText = (EditText) findViewById(R.id.outputText);
	
	usernameText = (EditText)findViewById(R.id.username);
	passwordText = (EditText)findViewById(R.id.password);
	
	
	Button commitButton = (Button) findViewById(R.id.button1);
	Button jumpbButton = (Button) findViewById(R.id.button2);
	//为跳转按钮添加监听
	jumpbButton.setOnClickListener(new jumpMonitor());
    }

    public void showToast(String s){
	Toast toast = Toast.makeText(this, s, Toast.LENGTH_LONG);
	toast.show();
    }
    class jumpMonitor implements OnClickListener {
	@Override
	public void onClick(View v) {
	    System.out.println("Commit button was pressed");
	    Intent nextActivity = new Intent(MainActivity.this,QQActivity.class);
	    startActivity(nextActivity);
	}

    }
    public void showFriendRelation(View source){
	String sqlString = "select * from User where username in (select friendname from FriendRelation where username = 'xuhao')";
	BmobQuery<User> query = new BmobQuery<User>();
	query.doSQLQuery(this,sqlString, new sqlListener());
    }
    class sqlListener extends SQLQueryListener<User> {
	@Override
	public void done(BmobQueryResult<User> result, BmobException e) {
	    if(e==null){//查询成功
		showToast("查询成功");
		stu_list = (List<User>) result.getResults();
		if(stu_list!=null && stu_list.size()>0){//查询成功，返回结果不空
//		    Iterator<User> iterator = stu_list.iterator();
//		    while (iterator.hasNext()) {
//			User s = iterator.next();
//			outputText.append(s.getObjectId()+s.getUserName());
//			}
		    showToast("登录成功");
		}
		else {//查询成功，返回结果为空
		    showToast("用户名或密码错误");
		}
	    }
	    else {//查询失败，出现异常
		showToast("错误码:"+e.getErrorCode()+"错误描述"+e.getMessage());
	    }
	  
	    }
	}
    public void addFriendRelation(View source){
	FriendRelation fRelation = new FriendRelation();
	fRelation.setUsername("wujing");
	fRelation.setFriendname("xuhao");
	fRelation.save(this, new SaveListener() {
	    
	    @Override
	    public void onSuccess() {
		showToast("插入成功");
	    }
	    
	    @Override
	    public void onFailure(int arg0, String arg1) {
		showToast("失败："+arg1);
		
	    }
	});
    }
    
    public void Validate(View source){
	String usernameString = usernameText.getText().toString();
	String passwordString = passwordText.getText().toString();
	String sqlString = "select * from User where username = '"+usernameString+"' and password = '"+passwordString+"' ";
	BmobQuery<User> query = new BmobQuery<User>();
	query.doSQLQuery(this,sqlString, new sqlListener());
	
    }
    }

