package com.xuhao.c_usercenter;


import java.util.List;

import cn.bmob.v3.listener.InitListener;

import com.xuhao.R;
import com.xuhao.application.MyApplication;
import com.xuhao.javaBean.User;
import com.xuhao.utility.Util;

import android.app.Application;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ritaa on 2015/8/1.
 * 用户中心界面
 */

public class UserCenterFragment extends Fragment{
    ImageView portraitView ;
    TextView nickNameTextView;
    TextView schoolTextView;
    TextView majorTextView;
    TextView genderTextView;
    TextView identityTextView;
    TextView descriptionTextView;
    View mBaseView;
    Context mContent;
    private Context mcontext;
    private MyApplication mApplication ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreateView(inflater, container, savedInstanceState);
        mcontext=getActivity();
        mBaseView=inflater.inflate(R.layout.fragement_usercenter,null);
        findView();
        init();
        return mBaseView;
    }
    private void findView(){
	portraitView = (ImageView)mBaseView.findViewById(R.id.my_picture);
	nickNameTextView = (TextView)mBaseView.findViewById(R.id.personal_info_nick_name);
	schoolTextView = (TextView)mBaseView.findViewById(R.id.school);
	majorTextView = (TextView)mBaseView.findViewById(R.id.major);
	genderTextView = (TextView)mBaseView.findViewById(R.id.gender);
	identityTextView = (TextView)mBaseView.findViewById(R.id.identity);
	descriptionTextView = (TextView)mBaseView.findViewById(R.id.description);
	mApplication = (MyApplication)getActivity().getApplication();
	mContent = getActivity();
	
    } 
    
    Bitmap portraitBitmap;
    final Handler handler = new Handler(){
  	@Override
  	public void handleMessage(Message msg){
  	    if(msg.what==0){
  		portraitView.setImageBitmap(portraitBitmap);
  	    }
  	    else{
  	    }
  	}
      };
    private void init(){
	//设置头像
	new Thread(){
	    @Override
	    public void run() {
		try{
		 portraitBitmap= Util.getbitmap(mApplication.getPresentUser().getPortrait().getFileUrl(mContent));
		}catch(NullPointerException exception){
		    Resources res=getResources();
			Bitmap bmp=BitmapFactory.decodeResource(res, R.drawable.login_default_avatar_min);
			portraitBitmap = bmp;
		}
		 handler.sendEmptyMessage(0);
	    }
	}.start();
	
	String nickName = mApplication.getPresentUser().getNickName();
	String school = mApplication.getPresentUser().getSchool();
	String major = mApplication.getPresentUser().getMajor();
	String gender = mApplication.getPresentUser().getGender();
	String identity = mApplication.getPresentUser().getIdentity();
	String description = mApplication.getPresentUser().getDescription();
	nickNameTextView.setText(nickName);
	schoolTextView.setText(school);
	majorTextView.setText(major);
	genderTextView.setText(gender);
	identityTextView.setText(identity);
	descriptionTextView.setText(description);
    }
    
    
    
    
    
    
    
    
}
