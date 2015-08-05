package com.xuhao.activity;

import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

import com.example.androidtestproject.R;
import com.xuhao.javaBean.User;

public class LoginActivity extends Activity {
    private Context mContext;
    private RelativeLayout rl_user;
    private Button mLoginButton;
    private Button mRegisterButton;
    private EditText mAccount;
    private EditText mPassword;
    private MyApplication mApplication;
    //用户名和密码的内容
    String accountString = null;
    String passwordString  = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_login);
        mContext = this;
        rl_user = (RelativeLayout) findViewById(R.id.rl_user);
        mLoginButton = (Button) findViewById(R.id.login);
        mRegisterButton = (Button) findViewById(R.id.register);
        mAccount = (EditText) findViewById(R.id.account);
        mPassword = (EditText) findViewById(R.id.password);
        Animation anim = AnimationUtils.loadAnimation(mContext,
                R.anim.login_anim);
        anim.setFillAfter(true);
        rl_user.startAnimation(anim);
        mApplication = (MyApplication)getApplication();

      
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	accountString = mAccount.getText().toString();
            	passwordString = mPassword.getText().toString();
            	String sqlString = "select * from User where username = '"+accountString+"' and password = '"+passwordString+"' ";
        	BmobQuery<User> query = new BmobQuery<User>();
        	query.doSQLQuery(LoginActivity.this,sqlString, new LogInListener());
              
            }
        });
        
        
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        	//这里填写注册功能以及界面
            }
        });
    }

    List<User> user_list = null;
    class LogInListener extends SQLQueryListener<User> {
	@Override
	public void done(BmobQueryResult<User> result, BmobException e) {
	    if(e==null){//查询成功
//showToast("查询成功");
		user_list = (List<User>) result.getResults();
		if(user_list!=null && user_list.size()>0){//查询成功，返回结果不空
		    //将当前用户作为全局变量存储起来
		    mApplication.setPresentUser(user_list.get(0));
		    showToast("登录成功,用户："+user_list.get(0).getUserName());
		    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
		    startActivity(intent);//进入主界面
		}
		else {//查询成功，返回结果为空
		    showToast("用户名或密码错误，请重新输入");
		    mAccount.setText("");
		    mPassword.setText("");
		}
	    }
	    else {//查询失败，出现异常
		showToast("错误码:"+e.getErrorCode()+"错误描述"+e.getMessage());
	    }
	  
	    }
	}

//以下是一些常用方法
    
    public void showToast(String s){
 	Toast toast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
 	toast.show();
     }
}
