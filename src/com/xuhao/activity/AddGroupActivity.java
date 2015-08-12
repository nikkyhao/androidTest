package com.xuhao.activity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.listener.SaveListener;

import com.example.androidtestproject.R;
import com.xuhao.javaBean.Group;
import com.xuhao.javaBean.GroupRelation;
import com.xuhao.javaBean.User;

import android.R.integer;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class AddGroupActivity extends Activity{
    
    private Context mContext;
    private List<User> mFriendList;
    private ListView mFriendListView;
    private AddGroupAdapter adapter;
    private TextView btn_creategroup;
    
    private MyApplication mApplication;
    private List<User> choosenFriendList;//用于保存用户打了对号的那些个用户
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_addgroup);
       mApplication =(MyApplication)getApplication();
        findView();
        init();
    }
    
    
    private void init() {
    	 mContext=this;
    	//  设置联系人
	List<User> friendList = mApplication.getFriendList();
	//用于用户点击ListView的其他地方也可以使checkBox状态改变
	  mFriendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view,
                                  int position, long id) {
          	
          }
      });
	  mFriendListView.setAdapter(new AddGroupAdapter(mContext, friendList));
	  //添加监听
	  btn_creategroup.setOnClickListener(new MyCreateGroupListener());
	  choosenFriendList = new ArrayList<User>();
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

    	    CheckBox cBox = (CheckBox)convertView.findViewById(R.id.checkBox);
            cBox.setOnCheckedChangeListener(new MyCheckedChangedListener(position));
            return convertView;
	}
	//此类是内部类AddGroupAdapter类的内部类，屌不屌！
	class MyCheckedChangedListener implements OnCheckedChangeListener{
		int position;
		public MyCheckedChangedListener(int position) {
			// TODO Auto-generated constructor stub
			this.position = position;
		}
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			choosenFriendList.add(mApplication.getFriendList().get(position));
		}
	}
	
	
    }
    
    
    
    
    class MyCreateGroupListener implements View.OnClickListener{
    	List<BmobObject> batch = new ArrayList<BmobObject>();
		@Override
		public void onClick(View v) {
			final Group newGroup = new Group();
			StringBuilder groupName = makeGroupName();
			newGroup.setName(groupName.toString());
			newGroup.save(mContext, new SaveListener() {
			    @Override
			    public void onSuccess() {
			    	batch.clear();
			    	for(int i =0;i<choosenFriendList.size();i++){
						GroupRelation relation= new GroupRelation();
						relation.setGroupId(newGroup.getObjectId());
						relation.setUserId(choosenFriendList.get(i).getObjectId());
						batch.add(relation);
						}
			    	GroupRelation relation = new GroupRelation();
			    	relation.setGroupId(newGroup.getObjectId());
			    	relation.setUserId(mApplication.getPresentUser().getObjectId());
			    	batch.add(relation);
			    	new BmobObject().insertBatch(mContext, batch, new SaveListener() {
			    	    @Override
			    	    public void onSuccess() {
			    	    	showToast("批量添加成功");
			    	    }
			    	    @Override
			    	    public void onFailure(int code, String msg) {
			    	    	showToast("addFailure："+msg);
			    	    }
			    	});
			    	mApplication.isGroupListChanged = true;//让GroupList显示的时候需要再从网络上获取一遍
			    }

			    @Override
			    public void onFailure(int code, String msg) {
			    	 showToast("创建数据失败：" + msg);
			    }
			});
			
		}
		private StringBuilder makeGroupName() {
			StringBuilder string = new StringBuilder();
			for(int i =0;i<choosenFriendList.size();i++){
				User user = choosenFriendList.get(i);
			string.append(user.getNickName()+"、");
			}
			string.append("的分组");
			return string;
		}
		
	}
    public void showToast(String s){
    	Toast toast = Toast.makeText(mContext, s, Toast.LENGTH_SHORT);
    	toast.show();
        }
}
