package com.xuhao.activity;

import android.app.Activity;
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

import com.example.androidtestproject.R;

public class LoginActivity extends Activity {
    private Context mContext;
    private RelativeLayout rl_user;
    private Button mLoginButton;
    private Button mRegisterButton;
    private EditText mAccount;
    private EditText mPassword;
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

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        	//这里填写注册功能以及界面
            }
        });
    }






}
