package com.xuhao.push;
import java.util.List;

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
		if(intent.getAction().equals(PushConstants.ACTION_MESSAGE)){
            Toast.makeText(context, "BmobPushDemo收到消息："+intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING), Toast.LENGTH_SHORT).show();
            receiveIterface.receiveMessage(intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING));
		}
	}
    
    public interface ReceiveMessageInterface{
	public void receiveMessage(String content);
    }

    public void setReceiveIterface(ReceiveMessageInterface receiveIterface) {
        this.receiveIterface = receiveIterface;
    }

}
