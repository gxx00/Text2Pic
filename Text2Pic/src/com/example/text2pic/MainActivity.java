package com.example.text2pic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.app.MyApplication;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private LinearLayout colorpick, plus, reduce;
	private EditText text;
	private LinearLayout linearlayout;
	private LinearLayout trans_linearlayout;
	private int width;
	private int height;
	private int statusbarheight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		initViews();
		doViews();
	}

	private void initWH() {
		DisplayMetrics outMetrics=new DisplayMetrics();
		// TODO Auto-generated method stub
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		width=outMetrics.widthPixels;
		height=outMetrics.heightPixels;
		Rect outRect=new Rect();
		getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
		statusbarheight=outRect.top;
	}

	

	private void doViews() {
		// TODO Auto-generated method stub
		plus.setOnClickListener(this);
		colorpick.setOnClickListener(this);
		reduce.setOnClickListener(this);
		trans_linearlayout.setOnClickListener(this);
		linearlayout.setOnClickListener(this);
	}
	private void initViews() {
		// TODO Auto-generated method stub
		colorpick = (LinearLayout) findViewById(R.id.main_colorpick);
		plus = (LinearLayout) findViewById(R.id.main_textplus);
		reduce = (LinearLayout) findViewById(R.id.main_reduce);
		text = (EditText) findViewById(R.id.main_text);
		linearlayout = (LinearLayout) findViewById(R.id.main_ll);
		trans_linearlayout = (LinearLayout) findViewById(R.id.main_ll1);
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
		if (id == R.id.share) {
			
			return true;
		}else if (id==R.id.save) {
			initWH();
			text.setCursorVisible(false);
			if (linearlayout.getVisibility()!=View.INVISIBLE) {
				linearlayout.setVisibility(View.INVISIBLE);
			}
			final Bitmap bitmap=genetePic();
			text.setCursorVisible(true);
			Intent intent=new Intent();
			intent.setClass(this, PicActivity.class);
			MyApplication.app.setBit(bitmap);
			startActivity(intent);
			overridePendingTransition(R.anim.anim_picenter, 0);
			if (linearlayout.getVisibility()==View.INVISIBLE) {
				linearlayout.setVisibility(View.VISIBLE);
			}
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void hideSoftIme() {
		// TODO Auto-generated method stub
		InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}



	

	private Bitmap genetePic() {
		// TODO Auto-generated method stub
		View view=getWindow().getDecorView();//获得底层的view
		view.setDrawingCacheEnabled(true);//支持view写入图片
		view.buildDrawingCache();//view写入图片
		Bitmap bitmap=view.getDrawingCache();//获取缓存中的图片
		Bitmap newbit=Bitmap.createBitmap(bitmap, 0, statusbarheight+getActionBar().getHeight(), width, height-statusbarheight-getActionBar().getHeight());
		view.destroyDrawingCache();
		return newbit;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.main_textplus:
			float textsize=text.getTextSize();
			text.setTextSize(TypedValue.COMPLEX_UNIT_PX,textsize + 2);
			break;
		case R.id.main_reduce:
			float textsize1=text.getTextSize();
			text.setTextSize(TypedValue.COMPLEX_UNIT_PX,textsize1- 2);
			break;
		case R.id.main_colorpick:
			showColorPickDialog(1);
			break;
		case R.id.main_ll1:
			if (linearlayout.getVisibility() == View.INVISIBLE)
				linearlayout.setVisibility(View.VISIBLE);
			break;
		case R.id.main_ll:
			if (linearlayout.getVisibility() == View.VISIBLE)
				linearlayout.setVisibility(View.INVISIBLE);
			break;
		}
		
	}

	private void showColorPickDialog(int i) {
		// TODO Auto-generated method stub
    ColorPickerDialogBuilder bulider=ColorPickerDialogBuilder.with(this)
    		.setTitle("选择背景颜色")
    		.initialColor(Color.WHITE)
    		.wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
    		.density(12)
    		.setOnColorSelectedListener(new OnColorSelectedListener() {
				
				@Override
				public void onColorSelected(int selectedColor) {
					// TODO Auto-generated method stub
					text.setBackgroundColor(selectedColor);
				}
			}).setNegativeButton("取消",new DialogInterface. OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			}).setPositiveButton("确定", new ColorPickerClickListener() {
				
				@Override
				public void onClick(DialogInterface d, int lastSelectedColor, Integer[] allColors) {
					// TODO Auto-generated method stub
					text.setBackgroundColor(lastSelectedColor);
				}
			});
    bulider.build().show();
	}

}
