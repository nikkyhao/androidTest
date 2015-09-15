package com.xuhao.c_message;
import java.util.Calendar;
import java.util.Date;

import android.R.integer;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import cn.bmob.v3.datatype.BmobDate;

import com.example.androidtestproject.R;
import com.xuhao.application.MyApplication;
import com.xuhao.javaBean.Messages;
import com.xuhao.utility.Util;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddPlansActivity extends Activity{
    int requestCode = 0;
    // 显示日期和时间的按钮
    RelativeLayout btn_selectdate, btn_selecttime;
    TextView Dateshow, Timeshow;
    // 完成按钮
    Button btn_finish;
    // 回退按钮
    ImageView btn_back;
    //日程内容显示框
    EditText contentEditText;
      MyApplication mApplication = null;
       private int year,month,day,hour,minute;
       private String groupId;
       Calendar currentTime = null;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		setContentView(R.layout.activity_addplans);
		findView();
		init();
   }
	
	private void findView() {
		// 分别是选择按钮
		btn_selectdate=(RelativeLayout)findViewById(R.id.select_date);
		Dateshow=(TextView)findViewById(R.id.date_show);
		btn_selecttime=(RelativeLayout)findViewById(R.id.select_time);
		Timeshow=(TextView)findViewById(R.id.time_show);
		btn_finish = (Button)findViewById(R.id.work_fragment_publish_btn);
		btn_back = (ImageView)findViewById(R.id.common_title_back);
		contentEditText = (EditText)findViewById(R.id.work_fragment_note);
	}
	private void init() {
		// TODO Auto-generated method stub
	//	Dateshow.setText("");
	    mApplication = (MyApplication)getApplication();
	    currentTime = Calendar.getInstance();
	    	initShowTimeAndDate();
	    	contentEditText.setText("要干什么呢");
	    	groupId=getIntent().getStringExtra("groupId");
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
//				Intent intent=new Intent(AddPlansActivity.this,SelectTimeActivity.class);
//				startActivityForResult(intent, requestCode);
			   
			    new TimePickerDialog(AddPlansActivity.this, 0, new TimePickerDialog.OnTimeSetListener() {
			        @Override
			        public void onTimeSet(TimePicker view, int hourOfDay, int cminute) {
			    	// TODO Auto-generated method stub
			    	hour = hourOfDay;
			    	minute = cminute;
			    	Timeshow.setText(hour+":"+minute);
			        }
			    }, hour, minute, true).show();
			}
		});
		btn_finish.setOnClickListener(new OnClickListener() {
		    
		    @Override
		    public void onClick(View v) {
			if (Dateshow.getText().equals("请选择日期")) {
			    Util.showToast(AddPlansActivity.this, "您还未选择日期");
			}
			if(Timeshow.getText().equals("请选择时间")){
			    Util.showToast(AddPlansActivity.this, "您还未选择时间");
			}
			else {//保证时间和日期都有了，现在可以传给数据库数据了
			    Util.showToast(AddPlansActivity.this,year+"-"+month+"-"+day);
			    Messages message = new Messages();
			    message.setExecute_Date(new BmobDate(new Date(year-1900,month,day,hour,minute)));
			    message.setRelease_date(new BmobDate(new Date()));
			    message.setContent(contentEditText.getText().toString());
			    message.setGroupId(groupId);
			    message.setSenderName(mApplication.getPresentUser().getNickName());
			    message.save(AddPlansActivity.this);//传到数据库
			}
			AddPlansActivity.this.finish();
			
		    }
		});
		btn_back.setOnClickListener(new OnClickListener() {
		    
		    @Override
		    public void onClick(View v) {
			AddPlansActivity.this.finish();
		    }
		});
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==0){
		Bundle bundle=data.getExtras();
		year=bundle.getInt("year");
		month=bundle.getInt("month");
		day=bundle.getInt("day");
		//Toast.makeText(this,year+"-"+month+"-"+day,Toast.LENGTH_LONG).show();
		Dateshow.setText(year+"-"+month+"-"+day);
		}else if(resultCode==1){
			Bundle bundle=data.getExtras();
			hour=bundle.getInt("hour");
			minute=bundle.getInt("minute");
			//Toast.makeText(this,year+"-"+month+"-"+day,Toast.LENGTH_LONG).show();
			Timeshow.setText(hour+":"+minute);	
		}
	}
	private void initShowTimeAndDate(){
	    year = currentTime.get(Calendar.YEAR);
	    month = currentTime.get(Calendar.MONTH);
	    day = currentTime.get(Calendar.DAY_OF_MONTH);
	    hour=currentTime.get(Calendar.HOUR_OF_DAY);
	    minute = currentTime.get(Calendar.MINUTE);
		Dateshow.setText(year+"-"+month+"-"+day);
		Timeshow.setText(hour+":"+minute);	
	}
}
