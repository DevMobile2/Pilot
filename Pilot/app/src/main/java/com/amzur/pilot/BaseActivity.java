package com.amzur.pilot;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

	protected MyApplication mMyApp;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	//	overridePendingTransition(R.anim.fade_bottom_to_top,R.anim.fade_top_to_bottom);
		mMyApp = (MyApplication)this.getApplicationContext();
	}
	@Override
	protected void onResume() {
		super.onResume();
		mMyApp.setCurrentActivity(this);
	}
	protected void onPause() {
		clearReferences();
		super.onPause();
	}
	protected void onDestroy() {
		clearReferences();
		super.onDestroy();
	}

	private void clearReferences(){
		Activity currActivity = mMyApp.getCurrentActivity();
		if (currActivity != null && currActivity.equals(this))
			mMyApp.setCurrentActivity(null);
	}
}
