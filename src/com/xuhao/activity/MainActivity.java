package com.xuhao.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v4.app.Fragment;
//import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.push.BmobPush;
import cn.bmob.v3.BmobInstallation;

import com.xuhao.R;
import com.xuhao.application.MyApplication;
import com.xuhao.c_friendlist.FriendListFragment;
import com.xuhao.c_message.MessageFragment;
import com.xuhao.c_schedule.ScheduleFragment;
import com.xuhao.c_usercenter.UserCenterFragment;


public class MainActivity extends Activity {

    protected static final String TAG = "MainActivity";
    private Context mContext;
    public ImageButton mNews,mConstact,mDeynaimic,mSetting;
    private View mPopView;
    private View currentButton;

    private TextView app_cancle;
    private TextView app_exit;
    private TextView app_change;

    private PopupWindow mPopupWindow;
    private LinearLayout buttomBarGroup;
    
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	//修改了此处的顺序
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏  这句话mac就不行，但是windows就行
        setContentView(R.layout.activity_main);
        mContext=this;
        FindView();
        init();
        
        //BmobPush 的初始化
        BmobInstallation.getCurrentInstallation(this).save();;
	BmobPush.startWork(this, MyApplication.APPId);
    }
    public void FindView(){
        mPopView= LayoutInflater.from(mContext).inflate(R.layout.app_exit, null);
        buttomBarGroup=(LinearLayout) findViewById(R.id.buttom_bar_group);
        mNews=(ImageButton) findViewById(R.id.buttom_news);
        mConstact=(ImageButton) findViewById(R.id.buttom_constact);
        mDeynaimic=(ImageButton) findViewById(R.id.buttom_deynaimic);
        mSetting=(ImageButton) findViewById(R.id.buttom_setting);
    }
    public void init(){//这个是好友列表
        mNews.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View v) {
                FragmentManager fm=getFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                MessageFragment messageFragment=new MessageFragment(MainActivity.this);
                ft.replace(R.id.fl_content, messageFragment,MainActivity.TAG);
                ft.commit();
                setButton(v);
            }
        });
        mConstact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//这个是分组的列表
                FragmentManager fm=getFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                FriendListFragment constactFatherFragment=new FriendListFragment();
                ft.replace(R.id.fl_content, constactFatherFragment, MainActivity.TAG);
                ft.commit();
                setButton(v);

            }
        });
        mDeynaimic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//这个是日程
                FragmentManager fm=getFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ScheduleFragment scheduleFragment=new ScheduleFragment(MainActivity.this);
                ft.replace(R.id.fl_content, scheduleFragment,MainActivity.TAG);
                ft.commit();
                setButton(v);
            }
        });
        mSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//这个是个人中心
                FragmentManager fm=getFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                UserCenterFragment userCenterFragment=new UserCenterFragment();
                ft.replace(R.id.fl_content, userCenterFragment,MainActivity.TAG);
                ft.commit();
                setButton(v);
            }
        });
       mConstact.performClick();//先显示联系人


    }
    private void setButton(View v){
        if(currentButton!=null&&currentButton.getId()!=v.getId()){
            currentButton.setEnabled(true);
        }
        v.setEnabled(false);
        currentButton=v;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    
}
