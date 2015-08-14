package com.xuhao.c_schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.datatype.BmobDate;

import com.example.androidtestproject.R;
import com.xuhao.javaBean.Messages;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnCreateContextMenuListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


/**
 * Created by ritaa on 2015/8/1.
 */
public class ScheduleFragment extends Fragment{
	 private Context mContext;
	 private View mBaseView;
	 private ListView mSchedule;
	 private List<Messages> msg;
     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	 super.onCreateView(inflater, container, savedInstanceState);
    	  mContext = getActivity();
          mBaseView = inflater.inflate(R.layout.fragment_schedule, null);
    	 findView();
    	 init();
    	 
    	 return mBaseView;
    }
		private void findView() {
			// TODO Auto-generated method stub
			mSchedule=(ListView)mBaseView.findViewById(R.id.my_schedule);
		}
		private void init() {
			// TODO Auto-generated method stub
			msg=new ArrayList<Messages>();
			Messages ms1=new Messages();
			Messages ms2=new Messages();
			ms1.setContent("跟舍友吃羊肉串");
			ms1.setExecute_Date(new BmobDate(new Date(2015-1900, 8, 13, 21,0)));
			ms2.setContent("叫董景磊写代码");
			ms2.setExecute_Date(new BmobDate(new Date(2015-1900,8,13,21,0)));
			msg.add(ms1);
			msg.add(ms2);
			ScheduleAdapter adapter=new ScheduleAdapter(mContext,msg);
			mSchedule.setAdapter(adapter);
		}
		class ScheduleAdapter extends BaseAdapter{
			Context mcontext;
			List<Messages> message; 
			private LayoutInflater mInflater;
			
			public ScheduleAdapter(Context context,List<Messages> mg){
				mcontext=context;
				 mInflater = LayoutInflater.from(context);
				message=mg;
			}
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return message.size();
			}

			@Override
			public Object getItem(int arg0) {
				// TODO Auto-generated method stub
				return message.get(arg0);
			}

			@Override
			public long getItemId(int arg0) {
				// TODO Auto-generated method stub
				return arg0;
			}

			@Override
			public View getView(int arg0, View arg1, ViewGroup arg2) {
				// TODO Auto-generated method stub
				arg1= mInflater.inflate(R.layout.schedule_item,null);
				TextView date=(TextView)arg1.findViewById(R.id.text_show_time);
				TextView text=(TextView)arg1.findViewById(R.id.title);
				ImageView photo=(ImageView)arg1.findViewById(R.id.image_1);
				Messages ms=message.get(arg0);
				date.setText(ms.getExecute_Date().getDate());
				text.setText(ms.getContent());
				
				
				return arg1;
			}		
		}
}
