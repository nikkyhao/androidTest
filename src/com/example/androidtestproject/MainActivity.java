package com.example.androidtestproject;

import cn.bmob.v3.Bmob;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Bmob.initialize(this, "6fd393e552ed1d8dc51dbccf1236cc32");
		Button commitButton = (Button)findViewById(R.id.button1);
		Button jumpbButton = (Button)findViewById(R.id.button2);
		jumpbButton.setOnClickListener(new jumpMonitor());
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
	
	
	class jumpMonitor implements OnClickListener{

	    @Override
	    public void onClick(View v) {
System.out.println("Commit button was pressed");
		Intent nextActivity = new Intent(MainActivity.this,SecondActivity.class);
		startActivity(nextActivity);
		
	    }
	    
	}
}
