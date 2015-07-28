package com.example.androidtestproject;

import java.util.Iterator;
import java.util.List;

import com.xuhao.javaBean.Student;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    List<Student> stu_list = null;
    EditText outputText = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	Bmob.initialize(this, "6fd393e552ed1d8dc51dbccf1236cc32");
	//获得组件
	outputText = (EditText) findViewById(R.id.outputText);
	Button commitButton = (Button) findViewById(R.id.button1);
	Button jumpbButton = (Button) findViewById(R.id.button2);
	//为跳转按钮添加监听
	jumpbButton.setOnClickListener(new jumpMonitor());
	getStudents();
	//向outputtext输出
	

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.main, menu);
	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	// Handle action bar item clicks here. The action bar will
	// automatically handle clicks on the Home/Up button, so long
	// as you specify a parent activity in AndroidManifest.xml.
	int id = item.getItemId();
	if (id == R.id.action_settings) {
	    return true;
	}
	return super.onOptionsItemSelected(item);
    }

    public void getStudents() {
	String sqlString = "select * from Student";
	BmobQuery<Student> query = new BmobQuery<Student>();
	query.doSQLQuery(MainActivity.this,sqlString,new sqlMonitor());
		
//		new SQLQueryListener<Student>(){
//
//	    @Override
//	    public void done(BmobQueryResult<Student> result, BmobException e) {
//		System.out.println("done 方法");
//	        if(e ==null){
//	            stu_list= (List<Student>) result.getResults();
//	            System.out.println("查询成功");
//	            if(stu_list!=null && stu_list.size()>0){
//	        	Iterator<Student> iterator = stu_list.iterator();
//	        	while (iterator.hasNext()) {
//	        	    Student s = iterator.next();
//	        	    outputText.append(s.getName());
//	        	}
//	            }else{
//	                System.out.println("smile"+"查询成功，无数据返回");
//	            }
//	        }else{
//	            System.out.print("smile"+ "错误码："+e.getErrorCode()+"+错误描述："+e.getMessage());
//	        }
//	    }
//	});
	
    }
    private void showToast(String s){
	Toast toast = Toast.makeText(this, s, Toast.LENGTH_LONG);
	toast.show();
    }
    
    class jumpMonitor implements OnClickListener {

	@Override
	public void onClick(View v) {
	    System.out.println("Commit button was pressed");
	    Intent nextActivity = new Intent(MainActivity.this,
		    SecondActivity.class);
	    startActivity(nextActivity);

	}

    }

    class sqlMonitor extends SQLQueryListener<Student> {
	@Override
	public void done(BmobQueryResult<Student> result, BmobException e) {
	    if(e==null){//查询成功
		showToast("查询成功");
	stu_list= (List<Student>) result.getResults();
		if(stu_list!=null && stu_list.size()>0){//查询成功，返回结果不空
		    stu_list = (List<Student>) result.getResults();
		    Iterator<Student> iterator = stu_list.iterator();
		    while (iterator.hasNext()) {
			Student s = iterator.next();
			outputText.append(s.getName());
			}
		}
		else {//查询成功，返回结果为空
		    
		}
	    }
	    else {//查询失败，出现异常
		System.out.println("错误码:"+e.getErrorCode()+"错误描述"+e.getMessage());
	    }
	  
	    }
	}
    
   }
