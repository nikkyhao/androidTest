package com.xuhao.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
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

import com.example.androidtestproject.R;
import com.example.androidtestproject.UserListActivity;
import com.xuhao.javaBean.*;

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
    private TitleBarView mTitleBarView;
    private ListView mFriendListView;
    private List<User> mFriendList;
    final Handler handler = new Handler(){
  	@Override
  	public void handleMessage(Message msg){
  	    if(msg.what==0){//表示图片
//  		userAdaper.setBmplist((List<Bitmap>)msg.obj);
//  		userAdaper.notifyDataSetChanged();
  		// myList.setAdapter(new userListAdapter(userList, (List<Bitmap>)msg.obj));
  		 mFriendListView.setAdapter(new FriendListAdapter(mContext,mFriendList, (List<Bitmap>)msg.obj));
  		System.out.println("图片已经获取");
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
        mTitleBarView = (TitleBarView) mBaseView.findViewById(R.id.title_bar);
        mFriendListView = (ListView)mBaseView.findViewById(R.id.friend_list_listview);
    }
    private void init() {
        //设置标题栏
        mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.VISIBLE);
        mTitleBarView.setTitleText("好友");
        mTitleBarView.setBtnLeft(R.string.control);//设置左上角的按钮 “管理”
        mTitleBarView.setBtnRight(R.drawable.qq_constact);//设置右上角的按钮"添加"
        getFriendRelation();
//        //设置联系人
//        mFriendList.add(new User("徐豪","这是一个逗比"));
//        mFriendList.add(new User("周强","这是一个超级逗比"));
//        mFriendList.add(new User("蕫景磊","这是一个究极逗比"));
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
            System.out.println("��ʼ��FriendAdapter");
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
           
            String name = mFriendList.get(position).getUserName();
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

    class sqlListener extends SQLQueryListener<User> {
   	@Override
   	public void done(BmobQueryResult<User> result, BmobException e) {
   	    if(e==null){//查询成功
   		showToast("查询成功");
   		mFriendList = (List<User>) result.getResults();
   		if(mFriendList!=null && mFriendList.size()>0){//查询成功，返回结果不空
   		    showToast("查询成功");
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
       		      bmp_list.add(getBitmap(iterator.next().getPortrait().getFileUrl(mContext)));
       		  }
                      Message msg = new Message();
                      msg.obj =bmp_list;
                      msg.what = 0;
                      handler.sendMessage(msg);
                  }                       
              }.start();
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
	Toast toast = Toast.makeText(mContext, s, Toast.LENGTH_LONG);
	toast.show();
    }
    public void getFriendRelation(){
	//这里的xuhao以后要换成用户的用户名
	String sqlString = "select * from User where username in (select friendname from FriendRelation where username = 'xuhao')";
	BmobQuery<User> query = new BmobQuery<User>();
	query.doSQLQuery(mContext,sqlString, new sqlListener());
    }
    
    public static Bitmap getBitmap(String path) {

	    URL url = null;
	    try {
		url = new URL(path);
	    } catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    HttpURLConnection conn = null;
	    try {
		conn = (HttpURLConnection)url.openConnection();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    conn.setConnectTimeout(5000);
	    try {
		conn.setRequestMethod("GET");
	    } catch (ProtocolException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    try {
		if(conn.getResponseCode() == 200){
		InputStream inputStream = conn.getInputStream();
		Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
		return bitmap;
		}
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    return null;

	    }

}
