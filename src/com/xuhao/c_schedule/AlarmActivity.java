package com.xuhao.c_schedule;

import com.xuhao.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import com.xuhao.javaBean.*;
//这个类是是时间到了自动弹出来的那个类
public class AlarmActivity extends Activity{
    MediaPlayer alarmMusic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	//alarmMusic = MediaPlayer.create(this,R.raw.alarm);
	//alarmMusic.setLooping(true);
	// 播放音乐
	//alarmMusic.start();
	
	
	//获得响应的Message对象
	Bundle bundle = getIntent().getExtras();
	Messages message = (Messages)bundle.getSerializable("message");
	// 创建一个对话框
	new AlertDialog.Builder(AlarmActivity.this).setTitle("闹钟")
		.setMessage("别忘了：\n"+message.getContent())
		.setPositiveButton("知道了", new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				// 停止音乐
				//alarmMusic.stop();
				// 结束该Activity
				AlarmActivity.this.finish();
			}
		}).show();
	
    }

}
