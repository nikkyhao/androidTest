package com.xuhao.c_schedule;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.xuhao.javaBean.Messages;
import com.xuhao.utility.DateUtil;
import com.xuhao.utility.Util;

public class AddAlarmRunnable implements Runnable {
    List<Messages> messages = null;
    AlarmManager aManager = null;
    Activity activity;
    Handler handler;

    public AddAlarmRunnable(List<Messages> messages, Activity activity,
	    Handler handler) {
	this.messages = messages;
	this.activity = activity;
	this.handler = handler;
	aManager = (AlarmManager) activity
		.getSystemService(Service.ALARM_SERVICE);

    }

    @Override
    public void run() {
	Intent intent = new Intent(activity, AlarmActivity.class);
	// 创建PendingIntent对象
	PendingIntent pi = PendingIntent.getActivity(activity, 0, intent, 0);
	Iterator<Messages> iterator = messages.iterator();
	while (iterator.hasNext()) {
	    Messages msg = iterator.next();
	    if (msg.isSaved()==true) {
		continue;
	    }
	    String date = msg.getExecute_Date().getDate();
	    Calendar c = Calendar.getInstance();
	    int minute = DateUtil.getMinute(date);
		int hour = DateUtil.getHour(date);
		int day = DateUtil.getDay(date);
		int month = DateUtil.getMonth(date);
		int year = DateUtil.getYear(date);
		Log.d("time", "minute:"+minute+
			"hour"+hour+
			"day:"+day+
			"month"+month+
			"year:"+year);
	    c.set(year,month,day,hour,minute);
	    aManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
	    msg.setSaved(true);
	    msg.update(activity);
	    Message message = new Message();
	    message.obj = date;
	    handler.sendMessage(message);
	}

    }

}
