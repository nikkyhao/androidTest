package com.xuhao.activity;

import java.io.File;
import java.net.PasswordAuthentication;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.InitListener;
import cn.bmob.v3.listener.SaveListener;

import com.xuhao.R;
import com.xuhao.javaBean.User;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Nickname;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity{
    EditText mid,mpsd,mname;
    RadioGroup msex , musertype;
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
	msex=(RadioGroup)findViewById(R.id.register_sex);
	mname=(EditText)findViewById(R.id.register_name);
	musertype=(RadioGroup)findViewById(R.id.register_usertype);
	btn_register=(TextView)findViewById(R.id.register_next);
    }

	private void init() {
		btn_register.setOnClickListener(new MyRegisterListener());
		msex.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		    
		    @Override
		    public void onCheckedChanged(RadioGroup group, int checkedId) {
			if(checkedId == R.id.sex_male){
			    sex = "男";
			}else {
			    sex = "女";
			}
		    }
		});
		musertype.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		    
		    @Override
		    public void onCheckedChanged(RadioGroup group, int checkedId) {
			if(checkedId == R.id.type_stu){
			    identity = "学生";
			}else {
			    identity = "老师";
			}
		    }
		});
	}
	String userid ;
    	String password ;
    	String sex  ;
    	String Nickname;
    	String identity;
    class MyRegisterListener implements OnClickListener{
    
		@Override
		public void onClick(View v) {
			String userid = mid.getText().toString();
		    	String password = mpsd.getText().toString();
		    	String Nickname = mname.getText().toString();
			
			User user = new User();
			user.setUserName(userid);
			user.setPassword(password);
			user.setSex(sex);
			user.setNickName(Nickname);
			user.setIdentity(identity);
			user.save(RegisterActivity.this, new SaveListener() {
				@Override
				public void onSuccess() {
					Toast toast = Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG);
				 	toast.show();
				}
				@Override
				public void onFailure(int arg0, String arg1) {
					Toast toast = Toast.makeText(RegisterActivity.this, "注册失败"+arg1, Toast.LENGTH_LONG);
				 	toast.show();
				}
			});
		}
    	
    }
}
