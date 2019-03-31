package com.example.synodemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.main.app.R;

public class GetActivity extends Activity{

	ImageView getImageView = null;
	EditText getEditText = null;
	TextView getTextView = null;
	Button signleGetButton = null;
	Button continuGetButton =null;
	Button exitContinuButton = null;
	Bitmap bmp1;
	int g_judge = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fingerprint_get);
		getImageView = (ImageView)findViewById(R.id.getImage);
		getEditText = (EditText)findViewById(R.id.getEditText);
		getTextView = (TextView)findViewById(R.id.getTextView);
		signleGetButton = (Button)findViewById(R.id.singleGetButton);
		//continuGetButton = (Button)findViewById(R.id.continuGetButton);
		//exitContinuButton = (Button)findViewById(R.id.exitContinuButton);

		/*
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile("/sdcard/picturetest/syno.jpg", opts);
		try {
			bmp1 = BitmapFactory.decodeFile("/sdcard/picturetest/syno.jpg");
		} catch (OutOfMemoryError e) {
			// TODO: handle exception
			System.out.println("find BMP1 error");
		}
		*/

		signleGetButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int iPageID = Integer.parseInt(getEditText.getText().toString());
				String myBmpPathString = "/system/picture/Finger.bmp";
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;
				Bitmap bm = BitmapFactory.decodeFile(myBmpPathString, options);
				/*
				 * here need to judge the pageID ,need to fix
				if( JNIJudgeIndex(iPageID) == 1)
				{
				}
				else if (JNIJudgeIndex(iPageID) == -1) {
					Toast toast1 = Toast.makeText(getApplicationContext(), "查找相关ID失败", Toast.LENGTH_SHORT);
					toast1.show();
					return;
				}
				*/
				while ( JNIGetImage()== 0x02 )
				{
					getTextView.setText(R.string.plaseputfinger);
					/*
					try {
						Thread.sleep(10);
					} catch (InterruptedException  e) {
						// TODO: handle exception
						System.out.println("sleep error");
					}
					*/
				}
				if(JNIGenChar(0xffffffff,0x01)!= 0)
				{
					getTextView.setText("finger 1 gen error");
					return;
				}
				if(JNIUpImage() != 0)
				{
					return;
				}
				getImageView.setImageBitmap(bm);
				//getTextView.setText(R.string.getsuccess);

				while (JNIGetImage()== 0)
				{
					getTextView.setText(R.string.getsuccess);
					/*
					try {
						Thread.sleep(100);
					} catch (InterruptedException  e) {
						// TODO: handle exception
						System.out.println("sleep error");
					}
					*/
				}
				Toast toast2 = Toast.makeText(getApplicationContext(), "请再放相同手指", Toast.LENGTH_SHORT);
				toast2.show();
				while (JNIGetImage()== 0x02)  //success
				{
					getTextView.setText(R.string.putsecondfinger);
					/*
					try {
						Thread.sleep(10);
					} catch (InterruptedException  e) {
						// TODO: handle exception
						System.out.println("sleep error");
					}
					*/
				}
				if(JNIUpImage() != 0)
				{
					return;
				}
				getImageView.setImageBitmap(bm);
				if(JNIGenChar(0xffffffff,0x02) != 0)
				{
					getTextView.setText("finger 2 genchar error");
					return;
				}
				getTextView.setText("获取图像成功");
				if(JNIRegModule() != 0)
				{
					getTextView.setText("combine error");
					return;
				}
				if(JNIStoreChar(0xffffffff,1,iPageID) != 0 )
				{
					getTextView.setText("StoreChar error");
					return;
				}
				getEditText.setText(Integer.toString(++iPageID));
				Toast toast3 = Toast.makeText(getApplicationContext(), "获取图片成功", Toast.LENGTH_SHORT);
				toast3.show();
				getTextView.setText(R.string.getsuccess);
			}
		});
		/*
		continuGetButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int iPageID = Integer.parseInt(getEditText.getText().toString());
				g_judge = 0;
				String myBmpPathString = "/system/picture/Finger.bmp";
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;
				Bitmap bm = BitmapFactory.decodeFile(myBmpPathString, options);
				int i_cnt;
				for( i_cnt = 0; i_cnt < 100 ; i_cnt++)
				{
				//finger 1
				getTextView.setText("please put your finger");
				while ( JNIGetImage()== 0x02 )
				{
				}
				if(JNIGenChar(0xffffffff,0x01)!= 0)
				{
					getTextView.setText("finger 1 gen error");
					return;
				}
				//put out finger
				while (JNIGetImage()== 0)
				{
				}
				
				//finger 2
				while ( JNIGetImage()== 0x02 )
				{
				}
				if(JNIUpImage() != 0)
				{
					return;
				}
				getImageView.setImageBitmap(bm);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO: handle exception
					return;
				}
				if(JNIGenChar(0xffffffff,0x02)!= 0)
				{
					getTextView.setText("finger 1 gen error");
					return;
				}
				
				if(JNIRegModule() != 0)
				{
					getTextView.setText("combine error");
					return;
				}
				if(JNIStoreChar(0xffffffff,1,iPageID) != 0 )
				{
					getTextView.setText("StoreChar error");
					return;
				}
				iPageID++;
				getEditText.setText(iPageID+" ");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO: handle exception
					return;
				}
				getTextView.setText("success");
				}
				return;
			}
		});
		exitContinuButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				g_judge = 1;
			}
		});
		*/

	}

	//single get finger 
	//return : 0 success , -1 faile
	public native int JNIGetImage();
	public native int JNIGenChar(int nAddr,int iBufferID);
	public native int JNIRegModule();
	public native int JNIStoreChar(int nAddr,int iBufferID, int iPageID);
	public native int JNIUpImage();
	public native int JNIJudgeIndex( int Index );

	static{
		System.loadLibrary("GetSyno");
	}

}
