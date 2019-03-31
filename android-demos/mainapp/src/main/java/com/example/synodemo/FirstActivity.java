package com.example.synodemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.main.app.R;

import java.io.File;

public class FirstActivity extends Activity {

	private Button myButtonOpen;
	private Button myButtonExit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fingerprint_first);
		myButtonOpen = (Button)findViewById(R.id.first_open);
		myButtonExit = (Button)findViewById(R.id.first_exit);
		//Judge the addr exist
		File file = new File("/system/syno");
		if(!file.exists())
		{
			file.mkdir();
		}
		myButtonOpen.setOnClickListener(new openOnClickLister());
		myButtonExit.setOnClickListener(new exitOnClickLister());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.first, menu);
		return true;
	}
	
	class openOnClickLister implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(JNIOpen()>=0)
			{
			Intent intent = new Intent();
			intent.setClass(FirstActivity.this, SecondActivity.class);
			FirstActivity.this.startActivity(intent);
			}
			else {
				Toast toast = Toast.makeText(getApplicationContext(), "open error", Toast.LENGTH_SHORT);
				toast.show();
				return;
			}
			if(JNIVfyPwd() != 0)
			{
				return;
			}
			JNIPutIndex2File();
		}
	}
	class exitOnClickLister implements View.OnClickListener{

		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			System.exit(0);
		}
	}

	
	public native int JNIOpen();
	public native int JNIAdd();
	public native int JNIVfyPwd();
	public native int JNIPutIndex2File();
	
	static{
		System.loadLibrary("SynoTest");
	}
	
	
}
