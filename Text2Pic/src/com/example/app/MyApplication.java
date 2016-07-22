package com.example.app;

import android.app.Application;
import android.graphics.Bitmap;

public class MyApplication extends Application {
	public static MyApplication app;
	private Bitmap bit;
@Override
public void onCreate() {
	// TODO Auto-generated method stub
	super.onCreate();
	app=this;
}
public Bitmap getBit() {
	return bit;
}
public void setBit(Bitmap bit) {
	this.bit = bit;
}

}
