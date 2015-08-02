package com.xuhao.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.example.androidtestproject.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


public class ChatActivity extends Activity {
	private TitleBarView mTitleBarView;
	private int friendId;
	private String friendName;
	private ListView chatMeessageListView;
	private ChatMessageAdapter chatMessageAdapter;
	private Button sendButton;
	private ImageButton emotionButton;
	private EditText inputEdit;
	private List<ChatEntity> chatList;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_chat);
		Intent intent = getIntent();
		friendName = intent.getStringExtra("friendName");
		friendId = intent.getIntExtra("friendId", 0);
		initViews();
		initEvents();
	}


	protected void initViews() {
		// TODO Auto-generated method stub
		mTitleBarView = (TitleBarView) findViewById(R.id.title_bar);
		mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE);
		mTitleBarView.setTitleText("与" + friendName + "对话");
		chatMeessageListView = (ListView) findViewById(R.id.chat_Listview);
		sendButton = (Button) findViewById(R.id.chat_btn_send);
		emotionButton = (ImageButton) findViewById(R.id.chat_btn_emote);
		inputEdit = (EditText) findViewById(R.id.chat_edit_input);

	}


	protected void initEvents() {
		chatMessageAdapter = new ChatMessageAdapter(ChatActivity.this,chatList);
		chatMeessageListView.setAdapter(chatMessageAdapter);
		sendButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String content = inputEdit.getText().toString();
				inputEdit.setText("");
				ChatEntity chatMessage = new ChatEntity();
				chatMessage.setContent(content);

				chatMessage.setReceiverId(friendId);
				chatMessage.setMessageType(ChatEntity.SEND);
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("MM-dd hh:mm:ss");
				String sendTime = sdf.format(date);
				chatMessage.setSendTime(sendTime);
				chatList.add(chatMessage);
			}
		});

		}
	class ChatMessageAdapter extends BaseAdapter {
		private List<ChatEntity> chatEntities;
		private LayoutInflater mInflater;
		private Context mContext0;

		public ChatMessageAdapter(Context context, List<ChatEntity> vector) {
			this.chatEntities = vector;
			mInflater = LayoutInflater.from(context);
			mContext0 = context;
		}

		@Override
		public int getCount() {
			return 0;
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
}


