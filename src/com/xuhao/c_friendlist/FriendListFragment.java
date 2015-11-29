package com.xuhao.c_friendlist;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

import com.xuhao.R;
import com.xuhao.application.MyApplication;
import com.xuhao.javaBean.*;
import com.xuhao.utility.Util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by ritaa on 2015/8/1.
 */
public class FriendListFragment extends Fragment{
    private Context mContext;
    private View mBaseView;
    private ListView mFriendListView;
    private List<User> mFriendList;
    
    ImageView addFriendImageView = null;
    
    //全局Application
    MyApplication mApplication = null;
    final Handler handler = new Handler(){
  	@Override
  	public void handleMessage(Message msg){
  	    if(msg.what==0){//表示图片
//  		userAdaper.setBmplist((List<Bitmap>)msg.obj);
//  		userAdaper.notifyDataSetChanged();
  		// myList.setAdapter(new userListAdapter(userList, (List<Bitmap>)msg.obj));
  		 mFriendListView.setAdapter(new FriendListAdapter(mContext,mFriendList, (List<Bitmap>)msg.obj));
  	    }
  	    else{//表示文字
//  		userAdaper.setUserList((List<User>)msg.obj);
//  		myList.setAdapter(userAdaper);
  		// myList.setAdapter(new userListAdapter((List<User>)msg.obj, null));
  		 mFriendListView.setAdapter(new FriendListAdapter(mContext,(List<User>)msg.obj, null));
  	    }
  	    
  	}
      };

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        mBaseView = inflater.inflate(R.layout.fragment_friendlist, null);
        findView();
        init();
        return mBaseView;
    }
    private void findView() {
        mFriendListView = (ListView)mBaseView.findViewById(R.id.friend_list_listview);
        addFriendImageView = (ImageView)mBaseView.findViewById(R.id.btn_friendlist_addfriend);
    }
    private void init() {
        //设置标题栏
        mApplication = (MyApplication)(getActivity().getApplication());
        addFriendImageView.setOnClickListener(new addFriendButtonListener());
        if(mApplication.isFriendListChanged){
        getFriendRelation();
//        showToast("从网络获取好友列表");
        mApplication.isFriendListChanged = false;
        }
        else if(mApplication.getFriendList()!=null){//如果不是第一次的话就从mApplication中获取
        	mFriendListView.setAdapter(new FriendListAdapter(mContext, mApplication.getFriendList(), mApplication.getBmp_list()));
        }
    }
    class FriendListAdapter extends BaseAdapter{
        private List<User> mFriendList;
        private LayoutInflater mInflater;
        List<Bitmap> bmplist = null;
        public FriendListAdapter(Context context, List<User> vector,List<Bitmap> bmplist) {
            super();
            this.mFriendList = vector;
            this.bmplist = bmplist;
            mInflater = LayoutInflater.from(context);
        }
        public int getCount() {
            return mFriendList.size();
        }

        public Object getItem(int position) {
            return mFriendList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView avatarView;
            TextView nameView;
            TextView introView;
            User user = mFriendList.get(position);
            //分别获取头像，姓名，以及个人介绍
           
            String name = mFriendList.get(position).getNickName();
            String briefIntro = mFriendList.get(position).getDescription();
            convertView = mInflater.inflate(R.layout.friend_list_item,
                    null); //表示一个好友的信息（一行）
          //头像
            avatarView = (ImageView) convertView
                    .findViewById(R.id.user_photo);
            //设置头像
            if(bmplist!=null){
                Bitmap photo=bmplist.get(position);
                avatarView.setImageBitmap(photo);
                }
            //姓名
            nameView = (TextView) convertView
                    .findViewById(R.id.friend_list_name);
            //个人介绍
            introView = (TextView) convertView
                    .findViewById(R.id.friend_list_brief);
            //将三个设置上去
          
            introView.setText(briefIntro);
            nameView.setText(name);

            return convertView;
        }
    }

    private class sqlListener extends SQLQueryListener<User> {
   	@Override
   	public void done(BmobQueryResult<User> result, BmobException e) {
   	    if(e==null){//查询成功
   		mFriendList = (List<User>) result.getResults();
   		mApplication.setFriendList(mFriendList);
   		if(mFriendList!=null && mFriendList.size()>0){//查询成功，返回结果不空
   		 Message message = new Message();
		    message.obj=mFriendList;
		    message.what = 1;//非图片信息
		    handler.sendMessage(message);
		    //新开一个线程下载图片
		    new Thread(){
                  @Override
                  public void run() {
             	 Iterator<User> iterator = mFriendList.iterator();
       		  List<Bitmap> bmp_list = new ArrayList<Bitmap>();
       		  while(iterator.hasNext()){
       		      //注意这里如果服务器上照片为空的话会报空指针错
       		      try {
       			  bmp_list.add(Util.getbitmap(iterator.next().getPortrait().getFileUrl(mContext)));
		    } catch (NullPointerException e ) {
			Resources res=getResources();
			Bitmap bmp=BitmapFactory.decodeResource(res, R.drawable.login_default_avatar_min);
			bmp_list.add(bmp);
		    }
       		  }
       		  mApplication.setBmp_list(bmp_list);
                      Message msg = new Message();
                      msg.obj =bmp_list;
                      msg.what = 0;
                      handler.sendMessage(msg);
                  }                       
              }.start();
   		}
   		else {//查询成功，返回结果为空
//   		    showToast("查询成功，返回结果为空");
   		}
   	    }
   	    else {//查询失败，出现异常
   		showToast("错误码:"+e.getErrorCode()+"错误描述"+e.getMessage());
   	    }
   	  
   	    }
   	}
    

    public void showToast(String s){
	Toast toast = Toast.makeText(mContext, s, Toast.LENGTH_SHORT);
	toast.show();
    }
    public void getFriendRelation(){
	//这里的xuhao以后要换成用户的用户名
	String sqlString = "select * from User where username in (select friendname from FriendRelation where username = '"+mApplication.getPresentUser().getUserName()+"')";
	BmobQuery<User> query = new BmobQuery<User>();
	query.doSQLQuery(mContext,sqlString, new sqlListener());
    }
    
    class addFriendButtonListener implements View.OnClickListener{
	@Override
	public void onClick(View v) {
	    // TODO Auto-generated method stub
	    Intent intent = new Intent(mContext,AddFriendActivity.class);
	    startActivityForResult(intent, 0);
	}
	
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
	 if(mApplication.isFriendListChanged){
	        getFriendRelation();
//	        showToast("从网络获取好友列表");
	        mApplication.isFriendListChanged = false;
	        }
	        else{//如果不是第一次的话就从mApplication中获取
	        	mFriendListView.setAdapter(new FriendListAdapter(mContext, mApplication.getFriendList(), mApplication.getBmp_list()));
	        }
    }
}
