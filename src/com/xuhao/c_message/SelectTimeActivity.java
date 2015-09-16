package com.xuhao.c_message;


import com.xuhao.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

public class SelectTimeActivity extends Activity{
	TimePicker timep;
	Button confirm;
	int hour=0,minute=0;
	int resultCode=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		setContentView(R.layout.activity_selecttime);
		findView();
		init();
	}
	
	private void findView() {
		// TODO Auto-generated method stub
		timep=(TimePicker)findViewById(R.id.timePicker1);
		confirm=(Button)findViewById(R.id.btn_selecttime_confirm);
	}
	private void init() {
		// TODO Auto-generated method stub
		confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.putExtra("hour", hour);
				intent.putExtra("minute",minute);
				setResult(resultCode, intent);
				SelectTimeActivity.this.finish();
			}
		});
		timep.setIs24HourView(true);
		hour=timep.getCurrentHour();
		minute=timep.getCurrentMinute();
		timep.setOnTimeChangedListener(new OnTimeChangedListener() {
			
			@Override
			public void onTimeChanged(TimePicker arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub
				hour=arg1;
				minute=arg2;
			}
		});
	}
}
