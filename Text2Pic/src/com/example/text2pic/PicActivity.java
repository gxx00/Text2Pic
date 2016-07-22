package com.example.text2pic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.app.MyApplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PicActivity extends Activity implements OnClickListener {
	private ImageView iview;
	private TextView ok, cancel;
	private Bitmap bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setTheme(R.style.mydialog);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_content);
		initViews();
		doViews();
	}

	private void doViews() {
		// TODO Auto-generated method stub
		bitmap=MyApplication.app.getBit();
		iview.setImageBitmap(bitmap);
		ok.setOnClickListener(this);
		cancel.setOnClickListener(this);
	}

	private void initViews() {
		// TODO Auto-generated method stub
		iview = (ImageView) findViewById(R.id.dialog_content_iv);
		ok = (TextView) findViewById(R.id.dialog_content_save);
		cancel = (TextView) findViewById(R.id.dialog_content_cancel);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.dialog_content_save:
			FileRev(bitmap);
			finish();
			break;
		case R.id.dialog_content_cancel:
			finish();
			break;

		}
	}

	protected void FileRev(Bitmap bitmap) {
		// TODO Auto-generated method stub
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			File dir = new File(Environment.getExternalStorageDirectory(), "text2pic");
			dir.mkdirs();
			File file = new File(dir, getTime() + ".png");
			Toast.makeText(getApplicationContext(), file.getAbsolutePath(), Toast.LENGTH_LONG).show();
			FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(file);
				bitmap.compress(CompressFormat.PNG, 100, fileOutputStream);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} else {

		}
	}

	private String getTime() {
		// TODO Auto-generated method stub
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");

		return simpleDateFormat.format(new Date());
	}

}
