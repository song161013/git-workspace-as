package com.example.synodemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.main.app.R;

public class SecondActivity extends Activity{

	private Button myButtonGet;
	private Button myButtonSearch;
	private Button myButtonLibrary;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fingerprint_second);
		myButtonGet=(Button)findViewById(R.id.second_get);
		myButtonSearch=(Button)findViewById(R.id.second_search);
		myButtonLibrary=(Button)findViewById(R.id.finger_lib);
		myButtonGet.setText(R.string.get);
		myButtonSearch.setText(R.string.search);
		myButtonLibrary.setText(R.string.lib);
		myButtonGet.setOnClickListener(new getOnClickLister());
		myButtonSearch.setOnClickListener(new searchOnClickLister());
		myButtonLibrary.setOnClickListener(new fingerLibOnclickLister());
	}
	
	class getOnClickLister implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(SecondActivity.this, GetActivity.class);
			SecondActivity.this.startActivity(intent);
		}
		
	}
	
	class searchOnClickLister implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(SecondActivity.this, SearchActivity.class);
			SecondActivity.this.startActivity(intent);
		}
		
	}

	class fingerLibOnclickLister implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(SecondActivity.this, FingerLib.class);
			SecondActivity.this.startActivity(intent);
		}
		
	}
}
