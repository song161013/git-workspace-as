package com.example.synodemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.main.app.R;

public class SearchActivity extends Activity{

	ImageView searchImageView = null;
	TextView searchTextView = null;
	Button signleSearchButton = null;
	Button continusearchButton =null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fingerprint_search);
		searchImageView = (ImageView)findViewById(R.id.searchImage);
		searchTextView = (TextView)findViewById(R.id.searchTextView);
		signleSearchButton = (Button)findViewById(R.id.signleSearchButton);
		continusearchButton = (Button)findViewById(R.id.continuSearchButton);
		signleSearchButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String myBmpPathString = "/system/picture/Finger.bmp";
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;
				Bitmap bm = BitmapFactory.decodeFile(myBmpPathString, options);
				while(JNISGetImage() == 0x02 )  
				{
				}
				if(JNISUpImage() != 0)
				{
					return;
				}
				searchImageView.setImageBitmap(bm);
				if( JNISGenChar(0xffffffff,1) != 0)
				{
					searchTextView.setText("search genchar error");
					return;
				}
				if(JNISearch() == 0)
				{
					searchTextView.setText("search success");
				}
				else 
				{
					searchTextView.setText("search error,not this finger");
				}
				return;
			}
		});
		continusearchButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				while ( (JNISGetImage()== 0x02)  )  
				{
					searchTextView.setText(R.string.plaseputfinger);
					/*
					try {
						Thread.sleep(10);
					} catch (InterruptedException  e) {
						// TODO: handle exception
						System.out.println("sleep error");
					}
					*/
				}
				if (JNISGenChar(0xffffffff,1) !=0)
				{
					searchTextView.setText("search genchar error");
					return;
				}
				if(JNISearch() == 0)
				{
					searchTextView.setText("search success");
				}
				else 
				{
					searchTextView.setText("search error,not this finger");
				}
				searchTextView.setText(R.string.searchsuccess);
			}
		});
	}
	
	public native int JNISGetImage();
	public native int JNISUpImage();
	public native int JNISGenChar(int nAddr,int iBufferID);
	public native int JNISearch();
	
	static{
		System.loadLibrary("SearchSyno");
	}

}
