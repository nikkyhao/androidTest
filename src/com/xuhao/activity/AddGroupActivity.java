package com.xuhao.activity;

import com.example.androidtestproject.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class AddGroupActivity extends Activity{
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_addgroup);
    }
    
    
    
    
    
    
    

}
