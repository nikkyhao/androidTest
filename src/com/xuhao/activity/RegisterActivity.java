package com.xuhao.activity;

import java.net.PasswordAuthentication;

import cn.bmob.v3.listener.InitListener;
import cn.bmob.v3.listener.SaveListener;

import com.example.androidtestproject.R;
import com.xuhao.javaBean.User;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Nickname;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
		btn_register.setOnClickListener(new MyRegisterListener());

	}
    
    class MyRegisterListener implements OnClickListener{
    	String userid = mid.getText().toString();
    	String password = mpsd.getText().toString();
    	String sex = msex.getText().toString();
    	String Nickname = mname.getText().toString();
    	String idnetity = musertype.getText().toString();
		@Override
		public void onClick(View v) {
			Log.d("userid", userid);
			
			
			User user = new User();
			user.setUserName(userid);
			user.setPassword(password);
			user.setSex(sex);
			user.setNickName(Nickname);
			user.setIdentity(idnetity);
			user.save(RegisterActivity.this, new SaveListener() {
				@Override
				public void onSuccess() {
					Toast toast = Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT);
				 	toast.show();
				}
				@Override
				public void onFailure(int arg0, String arg1) {
					Toast toast = Toast.makeText(RegisterActivity.this, "注册失败"+arg1, Toast.LENGTH_SHORT);
				 	toast.show();
				}
			});
		}
    	
    }
}
