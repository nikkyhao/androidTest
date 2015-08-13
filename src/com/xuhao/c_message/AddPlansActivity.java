package com.xuhao.c_message;
import android.app.Activity;
import android.content.Intent;

import com.example.androidtestproject.R;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AddPlansActivity extends Activity{
       ImageView btn_selectdate,btn_selecttime;
       int requestCode=0;
       TextView Dateshow,Timeshow;
   
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		setContentView(R.layout.activity_addplans);
		findView();
		init();
   }
	
	private void findView() {
		// TODO Auto-generated method stub
		btn_selectdate=(ImageView)findViewById(R.id.select_date);
		Dateshow=(TextView)findViewById(R.id.date_show);
		btn_selecttime=(ImageView)findViewById(R.id.select_time);
		Timeshow=(TextView)findViewById(R.id.time_show);
	}
	private void init() {
		// TODO Auto-generated method stub
	//	Dateshow.setText("");
		btn_selectdate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(AddPlansActivity.this,SelectDateActivity.class);
				startActivityForResult(intent, requestCode);
			}
		});
		btn_selecttime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(AddPlansActivity.this,SelectTimeActivity.class);
				startActivityForResult(intent, requestCode);
			}
		});
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==0){
		Bundle bundle=data.getExtras();
		int year=bundle.getInt("year");
		int month=bundle.getInt("month");
		int day=bundle.getInt("day");
		//Toast.makeText(this,year+"-"+month+"-"+day,Toast.LENGTH_LONG).show();
		Dateshow.setText(year+"-"+month+"-"+day);
		}else if(resultCode==1){
			Bundle bundle=data.getExtras();
			int hour=bundle.getInt("hour");
			int minute=bundle.getInt("minute");
			
			//Toast.makeText(this,year+"-"+month+"-"+day,Toast.LENGTH_LONG).show();
			Timeshow.setText(hour+":"+minute);	
		}
	}
}
