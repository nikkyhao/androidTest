package com.example.androidtestproject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

import com.xuhao.javaBean.User;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class UserListActivity extends Activity {
    ListView myList = null;
    List<User> userList;
    userListAdapter userAdaper = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_user_list); 
	myList = (ListView)findViewById(R.id.myLisy);
	showFriendRelation();
    }	
   
    class userListAdapter extends BaseAdapter{
	public void setUserList(List<User> userList) {
	    this.userList = userList;
	}

	public void setBmplist(List<Bitmap> bmplist) {
	    this.bmplist = bmplist;
	}

	public userListAdapter(List<User> userList, List<Bitmap> bmplist) {
	    super();
	    this.userList = userList;
	    this.bmplist = bmplist;
	}

	List<User> userList = null;
	List<Bitmap> bmplist = null;

	@Override
	public int getCount() {
	    // TODO Auto-generated method stub
	    return userList.size();
	}

	@Override
	public Object getItem(int position) {
	    // TODO Auto-generated method stub
	    return position;
	}

	@Override
	public long getItemId(int position) {
	    // TODO Auto-generated method stub
	    return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    LinearLayout line = new LinearLayout(UserListActivity.this);
	  		line.setOrientation(LinearLayout.HORIZONTAL);
	  		TextView text = new TextView(UserListActivity.this);
	  		ImageView image = new ImageView(UserListActivity.this);
	  		if(bmplist!=null){
	  		image.setImageBitmap(bmplist.get(position));
	  		}
	  		text.setText(userList.get(position).getUserName());
	  		text.setTextSize(20);
	  		text.setTextColor(Color.RED);
	  		line.addView(text);
	  		line.addView(image);
	  		// 返回LinearLayout实例
	  		return line;
	}
   
    
    }
    
    final Handler handler = new Handler(){
	@Override
	public void handleMessage(Message msg){
	    if(msg.what==0){//表示图片
//		userAdaper.setBmplist((List<Bitmap>)msg.obj);
//		userAdaper.notifyDataSetChanged();
		 myList.setAdapter(new userListAdapter(userList, (List<Bitmap>)msg.obj));
	    }
	    else{//表示文字
//		userAdaper.setUserList((List<User>)msg.obj);
//		myList.setAdapter(userAdaper);
		 myList.setAdapter(new userListAdapter((List<User>)msg.obj, null));
	    }
	    
	}
    };
    class sqlListener extends SQLQueryListener<User> {
   	@Override
   	public void done(BmobQueryResult<User> result, BmobException e) {
   	    if(e==null){//查询成功
   		showToast("查询成功");
   		userList = (List<User>) result.getResults();
   		if(userList!=null && userList.size()>0){//查询成功，返回结果不空
   		    Message message = new Message();
   		    message.obj=userList;
   		    message.what = 1;//非图片信息
   		    handler.sendMessage(message);
   		    //新开一个线程下载图片
   		    new Thread(){
                     @Override
                     public void run() {
                	 Iterator<User> iterator = userList.iterator();
          		  List<Bitmap> bmp_list = new ArrayList<Bitmap>();
          		  while(iterator.hasNext()){
          		      bmp_list.add(getBitmap(iterator.next().getPortrait().getFileUrl(UserListActivity.this)));
          		  }
                         Message msg = new Message();
                         msg.obj =bmp_list;
                         msg.what = 0;
                         handler.sendMessage(msg);
                     }                       
                 }.start();
   		    
//   		    //获取 URl
//   		  Iterator<User> iterator = user_list.iterator();
//   		  List<Bitmap> bmp_list = new ArrayList<Bitmap>();
//   		  while(iterator.hasNext()){
//   		      bmp_list.add(getBitmap(iterator.next().getPortrait().getFileUrl(UserListActivity.this)));
//   		  }
//   		 userAdaper = new userListAdapter(user_list,bmp_list);
//   		 handler.sendEmptyMessage(0x123);
   		}
   		else {//查询成功，返回结果为空
   		    showToast("查询成功，结果为空");
   		}
   	    }
   	    else {//查询失败，出现异常
   		showToast("错误码:"+e.getErrorCode()+"错误描述"+e.getMessage());
   	    }
   	  
   	    }
   	}
    
    public void showToast(String s){
	Toast toast = Toast.makeText(this, s, Toast.LENGTH_LONG);
	toast.show();
    }
    public void showFriendRelation(){
   	String sqlString = "select * from User where username in (select friendname from FriendRelation where username = 'xuhao')";
   	BmobQuery<User> query = new BmobQuery<User>();
   	query.doSQLQuery(this,sqlString, new sqlListener());
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
