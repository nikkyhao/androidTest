package com.xuhao.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.androidtestproject.R;
import com.xuhao.javaBean.User;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class AddGroupActivity extends Activity{
    
    private Context mContext;
    private List<User> mFriendList;
    private ListView mFriendListView;
    private AddGroupAdapter adapter;
    private TextView btn_creategroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_addgroup);
        mContext=this;
        findView();
        init();
    }
    
    
    private void init() {
	// TODO Auto-generated method stub
	//  设置联系人
	User user=new User();
	user.setUserName("xuhao");
	mFriendList=new ArrayList<User>();
      mFriendList.add(user);
      user.setUserName("zhouqiang");
      mFriendList.add(user);
      user.setUserName("mojin");
      mFriendList.add(user);
      mFriendListView.setAdapter(new AddGroupAdapter(mContext, mFriendList));
    }


    private void findView() {
	// TODO Auto-generated method stub
	btn_creategroup=(TextView)findViewById(R.id.btn_addgroup_create);
	mFriendListView=(ListView)findViewById(R.id.listview_addtogroup);
    }


    class AddGroupAdapter extends BaseAdapter{
	 private List<User> mFriendList;
	 private LayoutInflater mInflater;
	 List<Bitmap> bmplist = null;
         public AddGroupAdapter(Context context, List<User> vector) {
	    // TODO Auto-generated constructor stub
             mInflater = LayoutInflater.from(context);
             this.mFriendList = vector;
	}
	@Override
	public int getCount() {
	    // TODO Auto-generated method stub
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
	    // TODO Auto-generated method stub
	    CheckBox cBox;
	    ImageView avatarView;
            TextView nameView;
            TextView introView;
            User user = mFriendList.get(position);
            //分别获取头像，姓名，以及个人介绍
           
            String name = mFriendList.get(position).getUserName();
            String briefIntro = mFriendList.get(position).getDescription();
            convertView=mInflater.inflate(R.layout.addtogroup_item,
                    null); //表示一个好友的信息（一行）
          //头像
            avatarView = (ImageView) convertView
                    .findViewById(R.id.add_user_photo);
            //设置头像
            if(bmplist!=null){
                Bitmap photo=bmplist.get(position);
                avatarView.setImageBitmap(photo);
                }
            //姓名
            nameView = (TextView) convertView
                    .findViewById(R.id.addfriendtogroup_name);
            //个人介绍
            introView = (TextView) convertView
                    .findViewById(R.id.addfriendtogroup_brief);
            //将三个设置上去
          
            introView.setText(briefIntro);
            nameView.setText(name);

            return convertView;
	}
	
	
	
	
	
    }
    
    
    
    
    

}
