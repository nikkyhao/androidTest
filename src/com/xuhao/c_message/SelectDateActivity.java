package com.xuhao.c_message;


import java.util.Calendar;

import com.xuhao.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;

public class SelectDateActivity extends Activity{
	Button btn_confirm;
	CalendarView cal;
	int year=0,month=0,day=0;
	int resultCode=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		setContentView(R.layout.activity_selectdate);
		findView();
		init();
	}
	
	private void findView() {
		// TODO Auto-generated method stub
		btn_confirm=(Button)findViewById(R.id.btn_selectdate_confirm);
		cal=(CalendarView)findViewById(R.id.calendarView);
	}
	private void init() {
		// TODO Auto-generated method stub
		btn_confirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.putExtra("year", year);
				intent.putExtra("month", month);
				intent.putExtra("day", day);
				setResult(resultCode, intent);
				SelectDateActivity.this.finish();
			}
		});
		Calendar c = Calendar.getInstance();
		year=c.get(Calendar.YEAR);
		month=c.get(Calendar.MONTH);
		day=c.get(Calendar.DAY_OF_MONTH);
		cal.setOnDateChangeListener(new OnDateChangeListener() {
			@Override
			public void onSelectedDayChange(CalendarView arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				year=arg1;
				month=arg2;
				day=arg3;		
			}
		});
		
	}
}
