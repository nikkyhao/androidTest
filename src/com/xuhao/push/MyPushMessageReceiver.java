package com.xuhao.push;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.xuhao.application.MyApplication;
import com.xuhao.entity.ChatEntity;
import com.xuhao.utility.Util;

import cn.bmob.push.PushConstants;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyPushMessageReceiver extends BroadcastReceiver {
    private ReceiveMessageInterface receiveIterface;

    public MyPushMessageReceiver(ReceiveMessageInterface receiveMessageInterface) {
	// TODO Auto-generated constructor stub
	this.receiveIterface = receiveMessageInterface;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
	// TODO Auto-generated method stub
	if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
	    String jsonString = intent
		    .getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING);
//	    Toast.makeText(context, "收到消息啦：" + jsonString, Toast.LENGTH_SHORT)
//		    .show();
	    String content = "消息没收到唉";
	    String senderId = "不知道是谁发送的";
	    try {
		content = new JSONObject(
			new JSONObject(jsonString).getString("alert"))
			.getString("content");
		senderId =  new JSONObject(
			new JSONObject(jsonString).getString("alert"))
			.getString("senderId");
	    } catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    receiveIterface.receiveMessage(content, senderId);
	}
    }

    public interface ReceiveMessageInterface {
	public void receiveMessage(String content, String senderId);
    }

    public void setReceiveIterface(ReceiveMessageInterface receiveIterface) {
	this.receiveIterface = receiveIterface;
    }

}
