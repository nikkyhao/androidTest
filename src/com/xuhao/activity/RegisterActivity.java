package com.xuhao.activity;

import cn.bmob.v3.listener.InitListener;

import com.example.androidtestproject.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends Activity{
    EditText mid,mpsd,msex,mname,musertype;
    TextView btn_register;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_register);
        findView();
        init();
    }
  
    private void findView() {
	// TODO Auto-generated method stub
	mid=(EditText)findViewById(R.id.register_id);
	mpsd=(EditText)findViewById(R.id.register_password);
	msex=(EditText)findViewById(R.id.register_sex);
	mname=(EditText)findViewById(R.id.register_name);
	musertype=(EditText)findViewById(R.id.register_usertype);
	btn_register=(TextView)findViewById(R.id.register_next);
    }
    private void init() {
  	// TODO Auto-generated method stub
  	
	
	
	
	
	
	
      }
}
