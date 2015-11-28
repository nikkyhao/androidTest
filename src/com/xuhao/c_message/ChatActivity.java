package com.xuhao.c_message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.push.PushConstants;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

import com.xuhao.R;
import com.xuhao.application.MyApplication;
import com.xuhao.entity.ChatEntity;
import com.xuhao.javaBean.Messages;
import com.xuhao.utility.Util;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xuhao.push.*;

public class ChatActivity extends Activity implements MyPushMessageReceiver.ReceiveMessageInterface{
	private TextView title;
	private int friendId;
	private String friendName;
	private ListView chatMessageListView;
	private ChatMessageAdapter chatMessageAdapter;
	private Button sendButton;
	private ImageButton addPlanButton;
	private EditText inputEdit;
	private List<ChatEntity> chatList;
	private ImageView backButtonImageView;
	private MyApplication mApplication;
	
	private String groupId ;//分组的唯一标号，是Group的objectId
	//用于处理消息接收
	private Handler handler = new Handler(){//
	    @Override
		public void handleMessage(Message msg) {
		
		
		}
	    
	};
	
	//BmobPush
	BmobPushManager bmobPushManager;
	public void receiveMessage(String content,String senderId){
	 // 自己不能收到自己的消息
	    System.out.println("senderId:"+senderId);
	    System.out.println("myId:"+mApplication.getPresentUser().getObjectId());
	if (senderId.equals(mApplication.getPresentUser().getObjectId())) {
	    return;
	}
	    ChatEntity chatMessage = new ChatEntity();
		chatMessage.setContent(content);
		chatMessage.setReceiverId(12345);//这里应该是根据点击的界面确定，先设上一个值
		chatMessage.setMessageType(ChatEntity.RECEIVE);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd hh:mm:ss");
		String sendTime = sdf.format(date);
		chatMessage.setSendTime(sendTime);
		chatList.add(chatMessage);
		chatMessageAdapter = new ChatMessageAdapter(ChatActivity.this,chatList);
		chatMessageListView.setAdapter(chatMessageAdapter);
		chatMessageListView.setSelection(chatList.size());
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_chat);
		Intent intent = getIntent();
		friendName = intent.getStringExtra("friendName");
		friendId = intent.getIntExtra("friendId", 0);
		groupId = intent.getStringExtra("groupId");
		Log.d("present group ID",groupId);
		chatList = new ArrayList<ChatEntity>();
		bmobPushManager = new BmobPushManager(this);
		MyPushMessageReceiver receiver = new MyPushMessageReceiver(ChatActivity.this);
		IntentFilter intentFilter = new IntentFilter();
	        intentFilter.addAction("cn.bmob.push.action.MESSAGE");
		registerReceiver(receiver, intentFilter);//由于没有在menifest中注册，所以只能在这里注册了
		Util.showToast(this, "broadcast已设置");
		initViews();
		initEvents();
		findMessageOfGroup(groupId);
	}

	protected void initViews() {
		// TODO Auto-generated method stub
	        mApplication =(MyApplication)getApplication();
		title = (TextView) findViewById(R.id.chat_title_title);
		title.setText("与" + friendName + "对话");
		chatMessageListView = (ListView) findViewById(R.id.chat_Listview);
		sendButton = (Button) findViewById(R.id.chat_btn_send);
		addPlanButton = (ImageButton) findViewById(R.id.chat_btn_emote);
		inputEdit = (EditText) findViewById(R.id.chat_edit_input);
		backButtonImageView = (ImageView)findViewById(R.id.common_title_back);
	}

	protected void initEvents() {
		sendButton.setOnClickListener(new OnClickListener() {
		    //设置点击发送按钮的监听事件
			public void onClick(View v) {
				String content = inputEdit.getText().toString();
				inputEdit.setText("");
				ChatEntity chatMessage = new ChatEntity();
				chatMessage.setContent(content);
				chatMessage.setReceiverId(12345);//这里应该是根据点击的界面确定，先设上一个值
				chatMessage.setMessageType(ChatEntity.SEND);
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("MM-dd hh:mm:ss");
				String sendTime = sdf.format(date);
				chatMessage.setSendTime(sendTime);
				chatList.add(chatMessage);
				chatMessageAdapter = new ChatMessageAdapter(ChatActivity.this,chatList);
				chatMessageListView.setAdapter(chatMessageAdapter);
				chatMessageListView.setSelection(chatList.size());
				String senderId = ((MyApplication)ChatActivity.this.getApplication()).getPresentUser().getObjectId();
				String jsonString = "{\"content\":"+content+",\"senderId\":"+senderId+"}";
				bmobPushManager.pushMessageAll(jsonString);
				System.out.println("发送的jsonString："+jsonString);
			}
		});
		backButtonImageView.setOnClickListener(new OnClickListener() {
		    @Override
		    public void onClick(View v) {
			setResult(1);
			mApplication.isGroupListChanged = true;//这个其实不应该在这个地方写，但是以后再说吧
			ChatActivity.this.finish();
			
		    }
		});
		addPlanButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(ChatActivity.this,AddPlansActivity.class);
				intent.putExtra("groupId", groupId);
				startActivity(intent);
			}
		});	
	}
	class ChatMessageAdapter extends BaseAdapter {
		private List<ChatEntity> chatEntities;
		private LayoutInflater mInflater;
		private Context mContext;

		public ChatMessageAdapter(Context context, List<ChatEntity> vector) {
			this.chatEntities = vector;
			mInflater = LayoutInflater.from(context); 
			mContext = context;
		}

		@Override
		public int getCount() {
			return chatEntities.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			LinearLayout leftLayout;
			LinearLayout rightLayout;
			TextView leftMessageView;
			TextView rightMessageView;
			TextView timeView;
			ImageView leftPhotoView;
			ImageView rightPhotoView;
			view = mInflater.inflate(R.layout.chat_message_item_, null);
			ChatEntity chatEntity = chatEntities.get(position);
			leftLayout = (LinearLayout) view
					.findViewById(R.id.chat_friend_left_layout);
			rightLayout = (LinearLayout) view
					.findViewById(R.id.chat_user_right_layout);
			timeView = (TextView) view.findViewById(R.id.message_time);
			leftPhotoView = (ImageView) view
					.findViewById(R.id.message_friend_userphoto);
			rightPhotoView = (ImageView) view
					.findViewById(R.id.message_user_userphoto);
			leftMessageView = (TextView) view.findViewById(R.id.friend_message);
			rightMessageView = (TextView) view.findViewById(R.id.user_message);
			
			if (chatEntity.getMessageType() == ChatEntity.SEND) {
				rightLayout.setVisibility(View.VISIBLE);
				leftLayout.setVisibility(View.GONE);

				rightMessageView.setText(chatEntity.getContent());
			} else if (chatEntity.getMessageType() == ChatEntity.RECEIVE) {// 本身作为接收方
				leftLayout.setVisibility(View.VISIBLE);
				rightLayout.setVisibility(View.GONE);
				leftMessageView.setText(chatEntity.getContent());

			}
			return view;
		}
	}
	
	
	public void findMessageOfGroup(String groupId){
    	//对应分组的Id一一列出
    	String sqlString = "select * from Messages where groupId = '"+groupId+"'";
    	BmobQuery<Messages> query = new BmobQuery<Messages>();
    	query.doSQLQuery(this,sqlString, new messageListListener());
    }
    List<Messages> messageList;
    private class messageListListener extends SQLQueryListener<Messages>{
		@Override
		public void done(BmobQueryResult<Messages> result, BmobException e) {
			  if(e==null){//查询成功
		       		messageList = result.getResults();
		       		if(messageList!=null && messageList.size()>0){//查询成功，结果不为空
		       			showToast("Message查询成功，结果不为空");
		       			for(int i = 0;i<messageList.size();i++){
		           			Log.d("messagelist", messageList.get(i).getContent());
		           			}
		       		}
		       		else {//查询成功，返回结果为空
		       		    showToast("查询成功，返回结果为空");
		       		}
		       	    }
		       	    else {//查询失败，出现异常
		       		showToast("错误码:"+e.getErrorCode()+"错误描述"+e.getMessage());
		       	    }
			
		}
    	
    }
    
    public void showToast(String s){
    	Toast toast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
    	toast.show();
        }

}


