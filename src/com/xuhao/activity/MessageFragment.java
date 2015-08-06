package com.xuhao.activity;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;

import com.example.androidtestproject.R;
import com.xuhao.javaBean.Group;
import com.xuhao.javaBean.GroupRelation;
import com.xuhao.javaBean.User;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ritaa on 2015/8/1.
 */
public class MessageFragment extends Fragment {
    private Context mContext;
    private View mBaseView;
    private TitleBarView mTitleBarView;
    private List<MessageTabEntity> mMessageEntityList;
    private ListView mMessageListView;
    private FriendMessageAdapter adapter;
//    private BaseDialog mDialog;
//    private Handler handler;
    private int mPosition;
    private Button addGroupButton;
    private MessageTabEntity chooseMessageEntity;
    private MyApplication mApplication;
    //以下是实体类
    List<Group> grouplist;
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        mBaseView = inflater.inflate(R.layout.fragment_message, null);
        findView();
        init();
        return mBaseView;
    }
    private void findView() {
        mTitleBarView = (TitleBarView) mBaseView.findViewById(R.id.title_bar);
        
        mMessageListView = (ListView) mBaseView
                .findViewById(R.id.message_list_listview);
        addGroupButton = (Button)mBaseView.findViewById(R.id.addGroupButton);
    }
    private void init() {
        //设置标题栏
    	mApplication = (MyApplication)(getActivity().getApplication());
        mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE);
        mTitleBarView.setTitleText("消息");
        
        mMessageEntityList=new ArrayList<MessageTabEntity>();
        
        mMessageEntityList.add(new MessageTabEntity("徐豪","我睡了周强女友，你别跟他说","2015-8-1"));
        mMessageEntityList.add(new MessageTabEntity("周强","徐豪是不是睡了我女友","2015-8-1"));
        mMessageEntityList.add(new MessageTabEntity("张凯强","我看到徐豪睡了周强女友","2015-8-1"));

        
        adapter = new FriendMessageAdapter(mContext, mMessageEntityList);
        mMessageListView.setAdapter(adapter);
        mMessageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                    	showToast("item:"+position+"was clicked");
                    	if(position == 0){
                    		findMessageOfGroup("c22cf134a3");
                    	}
                        chooseMessageEntity = mMessageEntityList.get(position);
                        chooseMessageEntity.setUnReadCount(0);
                        adapter.notifyDataSetChanged();
                        mPosition = position;
                        //启动聊天窗口
                        Intent intent = new Intent(mContext, ChatActivity.class);
                        intent.putExtra("friendName", chooseMessageEntity.getName());
                        intent.putExtra("friendId", chooseMessageEntity.getSenderId());
                        startActivity(intent);
                        
                        //并不知道下面这些是判断什么的。。
                        if (chooseMessageEntity.getMessageType() == MessageTabEntity.MAKE_FRIEND_REQUEST)
                        //    mDialog.show()
                                            ;
                        else if (chooseMessageEntity.getMessageType() == MessageTabEntity.MAKE_FRIEND_RESPONSE_ACCEPT) {

                        } else {
                            
                        }
                    }
                });
        addGroupButton.setOnClickListener(new AddGroupListner());
        getGroupList();
        findMessageOfGroup("c22cf134a3");
    }
    class FriendMessageAdapter extends BaseAdapter {
        private List<MessageTabEntity> mMessageEntities;
        private LayoutInflater mInflater;
        private Context mContext0;
        public FriendMessageAdapter(Context context, List<MessageTabEntity> vector) {
            this.mMessageEntities = vector;
            mInflater = LayoutInflater.from(context);
            mContext0 = context;
        }
        public int getCount() {
            return mMessageEntities.size();
        }

        public Object getItem(int position) {
            return mMessageEntities.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView avatarView;
            TextView nameView;
            TextView unReadCountView;
            TextView sendTimeView;
            TextView contentView;
            MessageTabEntity message = mMessageEntities.get(position);
            Integer senderId = message.getSenderId();
            String name = message.getName();
            Bitmap photo=null;
            int messageType = message.getMessageType();
            String sendTime = message.getSendTime();
            int unReadCount = message.getUnReadCount();
            String content = message.getContent();
            convertView = mInflater.inflate(R.layout.fragment_message_item,null);
            avatarView = (ImageView) convertView.findViewById(R.id.user_photo);
            nameView = (TextView) convertView.findViewById(R.id.user_name);
            contentView = (TextView) convertView.findViewById(R.id.user_message);
            unReadCountView = (TextView) convertView
                    .findViewById(R.id.unread_message_count);
            sendTimeView = (TextView) convertView.findViewById(R.id.send_time);
            nameView.setText(name);
            sendTimeView.setText(sendTime);
            // System.out.println(messageType + "message类型");

            contentView.setText(content);
            return convertView;
        }
    }
    
    class AddGroupListner implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			User xuhao = new User(); xuhao.setObjectId("9e7b4ffd32");
			User wujing = new User(); wujing.setObjectId("e8bf30ca19");
			Group whuGroup = new Group();whuGroup.setObjectId("c22cf134a3");
	    	GroupRelation grelation = new GroupRelation();
	    	grelation.setGroupId(whuGroup.getObjectId());
	    	grelation.setUserId(xuhao.getObjectId());
	    	grelation.save(mContext, new SaveListener() {
	    	    @Override
	    	    public void onSuccess() {
	    	        // TODO Auto-generated method stub
	    	        showToast("添加数据成功:group");
	    	    }

	    	    @Override
	    	    public void onFailure(int code, String msg) {
	    	        // TODO Auto-generated method stub
	    	        showToast("创建数据失败：" + msg);
	    	    }
	    	});			
		}
    //添加人到指定分组
  
    }
    List<Message> messageList;
    private class groupListListener extends SQLQueryListener<Group> {
       	@Override
       	public void done(BmobQueryResult<Group> result, BmobException e) {
       	    if(e==null){//查询成功
       		showToast("查询成功");
       		grouplist = result.getResults();
       		if(grouplist!=null && grouplist.size()>0){//查询成功，结果不为空
       			showToast("查询成功，结果不为空");
       			for(int i = 0;i<grouplist.size();i++){
           			Log.d("grouplist", grouplist.get(i).getName());
           			}
       		}
       		else {//查询成功，返回结果为空
       		    showToast("查询成功，返回结果为空");
       		}
       	    }
       	    else {//查询失败，出现异常
       		showToast("错误码:"+e.getErrorCode()+"错误描述"+e.getMessage());
       	    }
       	    }}
    private class messageListListener extends SQLQueryListener<Message>{
		@Override
		public void done(BmobQueryResult<Message> result, BmobException e) {
			  if(e==null){//查询成功
		       		showToast("查询成功");
		       		messageList = result.getResults();
		       		if(messageList!=null && messageList.size()>0){//查询成功，结果不为空
		       			showToast("查询成功，结果不为空");
		       			for(int i = 0;i<messageList.size();i++){
		           			Log.d("messagelist", grouplist.get(i).getName());
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
    public void getGroupList(){
    	String sqlString = "select * from Group where objectId in (select groupId from GroupRelation where userId = '" +mApplication.getPresentUser().getObjectId() +"')";//此id时吴静的
    	BmobQuery<Group> query = new BmobQuery<Group>();
    	query.doSQLQuery(mContext,sqlString, new groupListListener());
        }
    public void findMessageOfGroup(String groupId){
    	String sqlString = "select * from Messages where groupId = '"+groupId+"'";
    	BmobQuery<Message> query = new BmobQuery<Message>();
    	query.doSQLQuery(mContext,sqlString, new messageListListener());
    }
    public void showToast(String s){
    	Toast toast = Toast.makeText(mContext, s, Toast.LENGTH_SHORT);
    	toast.show();
        }
}
