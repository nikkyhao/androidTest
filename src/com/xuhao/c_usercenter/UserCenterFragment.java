package com.xuhao.activity;


import com.example.androidtestproject.R;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ritaa on 2015/8/1.
 */
public class NearByFragment extends Fragment{
    View mBaseView;
    private Context mcontext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreateView(inflater, container, savedInstanceState);
        mcontext=getActivity();
        mBaseView=inflater.inflate(R.layout.fragement_usercenter,null);
        
        return mBaseView;
    }
}
