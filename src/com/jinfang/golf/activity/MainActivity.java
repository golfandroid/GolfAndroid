package com.jinfang.golf.activity;

import java.util.ArrayList;

import com.jinfang.golf.R;
import com.jinfang.golf.database.User;
import com.jinfang.golf.database.UserTable;
import com.jinfang.golf.utils.Tools;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.test).setOnClickListener(this);
		
		for(int i = 0; i < 10; i++){
			User mUser = new User();
			mUser.setmTimestamp(System.currentTimeMillis());
			mUser.setmUserID(Tools.getUUID()+"");
			mUser.setmUserName("张三"+i);
			mUser.setmUserPassword("aa"+i);
			UserTable.insertGroup(this, mUser);
		}
		new Thread(new MyThread()).start();
		ArrayList<User> userlist = UserTable.getAllGroup(this);
		for(User mUser :userlist){
			Log.e("============", "==="+mUser.getmUserName());
		}
	}
	
	class MyThread extends Thread{

		@Override
		public void run() {
			super.run();
			for(int i = 0; i < 10; i++){
				User mUser = new User();
				mUser.setmTimestamp(System.currentTimeMillis());
				mUser.setmUserID(Tools.getUUID()+"");
				mUser.setmUserName("张三"+i);
				mUser.setmUserPassword("aa"+i);
				UserTable.insertGroup(MainActivity.this, mUser);
			}
			ArrayList<User> userlist = UserTable.getAllGroup(MainActivity.this);
			for(User mUser :userlist){
				Log.e("======222222======", "==="+mUser.getmUserName());
			}
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.test:
			Intent intent = new Intent(this, JsonRequestActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
		
	}

}
