package com.xuhao.c_message;

//import android.support.v4.app.Fragment;
//import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.app.Fragment;
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

import com.xuhao.R;
import com.xuhao.activity.MainActivity;
import com.xuhao.application.MyApplication;
import com.xuhao.entity.GroupListEntity;
import com.xuhao.javaBean.Group;
import com.xuhao.javaBean.GroupRelation;
import com.xuhao.javaBean.Messages;
import com.xuhao.javaBean.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ritaa on 2015/8/1.
 *
 */
public class MessageFragment extends Fragment {
    private Context mContext;
    private View mBaseView;
    private List<GroupListEntity> mMessageEntityList;
    private ListView mMessageListView;
    private FriendMessageAdapter adapter;
//    private BaseDialog mDialog;
//    private Handler handler;
    private int mPosition;
    private TextView btn_addgroup;
    private GroupListEntity chooseMessageEntity;
    private MyApplication mApplication;
    private MainActivity mainActivity;
    //以下是实体类
    public MessageFragment(MainActivity mainActivity){
	this.mainActivity = mainActivity;
    }
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        mBaseView = inflater.inflate(R.layout.fragment_message, null);
        findView();
        init();
        return mBaseView;
    }
    private void findView() {
        btn_addgroup=(TextView)mBaseView.findViewById(R.id.message_btn_addgroup);
        mMessageListView = (ListView) mBaseView
                .findViewById(R.id.message_list_listview);
     
    }
    private void init() {
        //设置标题栏 
    	mApplication = (MyApplication)(getActivity().getApplication());
        mMessageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
//                    	showToast("item:"+position+"was clicked");
                        chooseMessageEntity = mMessageEntityList.get(position);
                        chooseMessageEntity.setUnReadCount(0);
                        adapter.notifyDataSetChanged();
                        mPosition = position;
                        //启动聊天窗口
                        Intent intent = new Intent(mContext, ChatActivity.class);
                        intent.putExtra("friendName", chooseMessageEntity.getName());
                        intent.putExtra("friendId", chooseMessageEntity.getSenderId());
                        intent.putExtra("groupId", chooseMessageEntity.getGroupId());
                        startActivityForResult(intent, 2);
                        
                        //并不知道下面这些是判断什么的。。
                        if (chooseMessageEntity.getMessageType() == GroupListEntity.MAKE_FRIEND_REQUEST)
                        //    mDialog.show()
                                            ;
                        else if (chooseMessageEntity.getMessageType() == GroupListEntity.MAKE_FRIEND_RESPONSE_ACCEPT) {

                        } else {
                            
                        }
                    }
                });
        btn_addgroup.setOnClickListener(new AddGroupListner() );
        if(mApplication.isGroupListChanged){//如果数据更改过则要重新获取一次
        getGroupList();
        mApplication.isGroupListChanged = false;
        }else if(mApplication.getGroupList()!=null){//如果远程数据库那边没有更改过那直接就把现有的放上了
        	List<Group> grouplist =mApplication.getGroupList();
        	 mMessageEntityList=new ArrayList<GroupListEntity>();
       		 for(int i =0;i<grouplist.size();i++){
       			 Group current=grouplist.get(i);
       			 mMessageEntityList.add(new GroupListEntity(current.getName(),current.getName(),current.getUpdatedAt(),current.getCreatedAt(),current.getObjectId()));
       		 	}
       		 adapter = new FriendMessageAdapter(mContext, mMessageEntityList);
             mMessageListView.setAdapter(adapter);

		}
    }
    class FriendMessageAdapter extends BaseAdapter {
        private List<GroupListEntity> mMessageEntities;
        private LayoutInflater mInflater;
        private Context mContext0;
        public FriendMessageAdapter(Context context, List<GroupListEntity> vector) {
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
            TextView createTimeView;
            GroupListEntity message = mMessageEntities.get(position);
            Integer senderId = message.getSenderId();
            String name = message.getName();
            //原数据
            Bitmap photo=null;
            int messageType = message.getMessageType();
            String sendTime = message.getSendTime();
            int unReadCount = message.getUnReadCount();
            String createTime = message.getCreateTime();
            //初始化各个view
            convertView = mInflater.inflate(R.layout.fragment_message_item,null);
            avatarView = (ImageView) convertView.findViewById(R.id.user_photo);
            nameView = (TextView) convertView.findViewById(R.id.user_name);
            createTimeView = (TextView) convertView.findViewById(R.id.user_message);
            unReadCountView = (TextView) convertView
                    .findViewById(R.id.unread_message_count);
            sendTimeView = (TextView) convertView.findViewById(R.id.send_time);
            //为各个view赴上元素值
            nameView.setText(name);
            createTimeView.setText("创建于:"+createTime.substring(0, 11));
            sendTimeView.setText(sendTime.substring(11, sendTime.length()));
            // System.out.println(messageType + "message类型");
            return convertView;
        }
    }
    
    class AddGroupListner implements View.OnClickListener{
		@Override
		public void onClick(View v) {
		    Intent intent=new Intent(mContext,AddGroupActivity.class);
		    startActivityForResult(intent, 0);
		}
    //添加人到指定分组
  
    }
    private class groupListListener extends SQLQueryListener<Group> {
    	List<Group> grouplist;
       	@Override
       	public void done(BmobQueryResult<Group> result, BmobException e) {
       	    if(e==null){//查询成功
       		grouplist = result.getResults();
       		if(grouplist!=null && grouplist.size()>0){//查询成功，结果不为空
//       			showToast("group查询成功，结果不为空");
       			mApplication.setGroupList(grouplist);
       		 mMessageEntityList=new ArrayList<GroupListEntity>();
       		 for(int i =0;i<grouplist.size();i++){
       			 Group current=grouplist.get(i);
       			 mMessageEntityList.add(new GroupListEntity(current.getName(),current.getName(),current.getUpdatedAt(),current.getCreatedAt(),current.getObjectId()));
       		 	}
       		 adapter = new FriendMessageAdapter(mContext, mMessageEntityList);
             mMessageListView.setAdapter(adapter);
       		 }
       		else {//查询成功，返回结果为空
//       		    showToast("查询成功，返回结果为空");
       		}
       	    }
       	    else {//查询失败，出现异常
       		showToast("错误码:"+e.getErrorCode()+"错误描述"+e.getMessage());
       	    }
       	    }}
    
    
    
    public void getGroupList(){
    	//对应当前用户的分组一一列出
    	String sqlString = "select * from Group where objectId in (select groupId from GroupRelation where userId = '" +mApplication.getPresentUser().getObjectId() +"')";
    	BmobQuery<Group> query = new BmobQuery<Group>();
    	query.doSQLQuery(mContext,sqlString, new groupListListener());
        }

    public void showToast(String s){
    	Toast toast = Toast.makeText(mContext, s, Toast.LENGTH_SHORT);
    	toast.show();
        }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
	if(resultCode ==5){//是addGroup 的返回
	if(mApplication.isGroupListChanged){
	        getGroupList();
	        mApplication.isGroupListChanged = false;
	        }else {
	        	List<Group> grouplist =mApplication.getGroupList();
	        	 mMessageEntityList=new ArrayList<GroupListEntity>();
	       		 for(int i =0;i<grouplist.size();i++){
	       			 Group current=grouplist.get(i);
	       			 mMessageEntityList.add(new GroupListEntity(current.getName(),current.getName(),current.getUpdatedAt(),current.getCreatedAt(),current.getObjectId()));
	       		 	}
	       		 adapter = new FriendMessageAdapter(mContext, mMessageEntityList);
	             mMessageListView.setAdapter(adapter);
			}
	}else if (resultCode == 1){//是ChatActivity的返回
	    if(mApplication.isGroupListChanged){
	        getGroupList();
	        mApplication.isGroupListChanged = false;
	        }else {
	        	List<Group> grouplist =mApplication.getGroupList();
	        	 mMessageEntityList=new ArrayList<GroupListEntity>();
	       		 for(int i =0;i<grouplist.size();i++){
	       			 Group current=grouplist.get(i);
	       			 mMessageEntityList.add(new GroupListEntity(current.getName(),current.getName(),current.getUpdatedAt(),current.getCreatedAt(),current.getObjectId()));
	       		 	}
	       		 adapter = new FriendMessageAdapter(mContext, mMessageEntityList);
	       		 mMessageListView.setAdapter(adapter);
			}
	}
    }
    
    
}
