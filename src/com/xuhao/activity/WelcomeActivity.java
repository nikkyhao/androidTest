package com.xuhao.activity;



import java.util.Date;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobDate;

import com.example.androidtestproject.R;
import com.xuhao.javaBean.Messages;
import com.xuhao.utility.SpUtil;
import com.xuhao.utility.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Window;
import android.widget.ImageView;

public class WelcomeActivity extends Activity {
	protected static final String TAG = "WelcomeActivity";
	private Context mContext;
	private ImageView mImageView;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		setContentView(R.layout.activity_welcome);
		mContext = this;
		findView();
		init();
		//一下纯属做测试
//		Messages mgs = new Messages();
//		mgs.setExecute_Date(new BmobDate(new Date()) );
//		String s = mgs.getExecute_Date().getDate();
//		System.out.println(mgs.getExecute_Date().getDate());;
//		Util.showToast(this, s);
		
		
	}

	private void findView() {
		mImageView = (ImageView) findViewById(R.id.iv_welcome);
	}

	private void init() {
		mImageView.postDelayed(new Runnable() {
			@Override
			public void run() {
				SpUtil.getInstance();
				sp = SpUtil.getSharePerference(mContext);
				SpUtil.getInstance();
				boolean isFirst = SpUtil.isFirst(sp);
				if (!isFirst) {
					SpUtil.getInstance();
					SpUtil.setBooleanSharedPerference(sp,
							"isFirst", true);
					Intent intent = new Intent(mContext, LoginActivity.class);
					startActivity(intent);
					finish();
				} else {
					Intent intent = new Intent(mContext, LoginActivity.class);
					startActivity(intent);
					finish();
				}
			}
		},2000);
		
	}
}
