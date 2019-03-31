package com.example.synodemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.main.app.R;

public class FingerLib extends Activity{

	TextView remindTextView = null;
	TextView resultTextView = null;
	Button upButton = null;
	Button downButton =null;
	Button cleanButton = null;
	EditText libEditText = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fingerprint_fingerlib);
		remindTextView = (TextView)findViewById(R.id.remind);
		resultTextView = (TextView)findViewById(R.id.result);
		upButton = (Button)findViewById(R.id.upFingerLib);
		downButton = (Button)findViewById(R.id.downFingerLib);
		libEditText = (EditText)findViewById(R.id.libEdit);
		upButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(JNIUpFingerLib() == -1)
				{
					resultTextView.setText("upload finger library error!");
					return;
				}
				else 
				{
					resultTextView.setText("upload finger library ok!");
				}
			}
		});
		downButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int index = Integer.parseInt(libEditText.getText().toString());
				if(JNIDownFingerLib(index) == -1)
				{
					resultTextView.setText("download finger library error!");
					return;
				}
				else 
				{
					resultTextView.setText("download finger library ok!");
				}
			}
		});
		cleanButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if( JNICleanFingerLib() != 0)
				{
					resultTextView.setText("clean error,please try it again");
					return;
				}
				resultTextView.setText("clean finger library ok!");
			}
		});
	}
	public native int JNIUpFingerLib();
	public native int JNIDownFingerLib(int Index);
	public native int JNICleanFingerLib();
	public native int JNIFJudgeIndex( int Index );
	
	static{
		System.loadLibrary("FingerLib");
	}
	

}
