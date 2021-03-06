package com.xuhao.c_schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

import com.xuhao.R;
import com.xuhao.activity.MainActivity;
import com.xuhao.application.MyApplication;
import com.xuhao.javaBean.Messages;
import com.xuhao.javaBean.User;
import com.xuhao.utility.Util;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnCreateContextMenuListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by ritaa on 2015/8/1.
 */
public class ScheduleFragment extends Fragment {
    private Activity mContext;
    private View mBaseView;
    private ListView mSchedule;
    private List<Messages> messages;
    private MyApplication mApplication;
    private MainActivity mainActivity;
    public ScheduleFragment(MainActivity mainActivity) {
	// TODO Auto-generated constructor stub
	this.mainActivity = mainActivity;
	
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreateView(inflater, container, savedInstanceState);
	mContext = getActivity();
	mBaseView = inflater.inflate(R.layout.fragment_schedule, null);
	mApplication = (MyApplication) getActivity().getApplication();
	findView();
	init();

	return mBaseView;
    }

    private void findView() {
	// TODO Auto-generated method stub
	mSchedule = (ListView) mBaseView.findViewById(R.id.my_schedule);
    }

    private void init() {
	getMessages();
    }

    class ScheduleAdapter extends BaseAdapter {
	Context mcontext;
	List<Messages> message;
	private LayoutInflater mInflater;

	public ScheduleAdapter(Context context, List<Messages> mg) {
	    mcontext = context;
	    mInflater = LayoutInflater.from(context);
	    message = mg;
	}

	@Override
	public int getCount() {
	    // TODO Auto-generated method stub
	    return message.size();
	}

	@Override
	public Object getItem(int arg0) {
	    // TODO Auto-generated method stub
	    return message.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
	    // TODO Auto-generated method stub
	    return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
	    // TODO Auto-generated method stub
	    arg1 = mInflater.inflate(R.layout.schedule_item, null);
	    TextView date = (TextView) arg1.findViewById(R.id.text_show_time);
	    TextView text = (TextView) arg1.findViewById(R.id.title);
	    TextView sender = (TextView) arg1.findViewById(R.id.message_sender);
	    ImageView photo = (ImageView) arg1.findViewById(R.id.image_1);
	    Messages ms = message.get(arg0);
	    String senderString = ms.getSenderName();
	    if (sender != null) {
		if (senderString.equals(mApplication.getPresentUser().getNickName())) {
		    senderString = "自己";
		}//如果是自己发的会在通知栏上显示自己
		sender.setText(senderString);
	    }
	    date.setText(ms.getExecute_Date().getDate());
	    text.setText(ms.getContent());

	    return arg1;
	}
    }

    private void getMessages() {
	String sql = "select * from Messages where groupId in (select groupId from GroupRelation where userId='"
		+ mApplication.getPresentUser().getObjectId() + "')";
	BmobQuery<Messages> query = new BmobQuery<Messages>();
	query.doSQLQuery(mContext, sql, new getMessageListener());
    }

    private class getMessageListener extends SQLQueryListener<Messages> {
	@Override
	public void done(BmobQueryResult<Messages> result, BmobException e) {
	    if (e == null) {// 查询成功
		messages = result.getResults();
		if (messages != null && messages.size() > 0) {// 查询成功且结果不为空
		    ScheduleAdapter adapter = new ScheduleAdapter(mContext,
			    messages);
		    mSchedule.setAdapter(adapter);
		    mSchedule.setSelection(messages.size());
		    mApplication.setMessageList(messages);
		    //新开一个线程设置闹钟
		    new Thread(new AddAlarmRunnable(messages, mContext,alarmHandler)).start();
		} else {// 查询成功，返回结果为空

//		    Util.showToast(mContext, "查询成功，返回结果为空");
		}
	    } else {// 查询失败，出现异常
		Util.showToast(mContext,
			"错误码:+e.getErrorCode()" + "错误描述" + e.getMessage());
	    }

	}
    }

    Handler alarmHandler = new Handler(){
	@Override
	public void handleMessage(Message msg) {
	    Util.showToast(mContext, "闹钟"+(String)msg.obj+"设置成功啦");
	}
	
    };
    
}
