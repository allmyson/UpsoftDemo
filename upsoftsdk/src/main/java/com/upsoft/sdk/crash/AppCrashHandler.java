package com.upsoft.sdk.crash;

import android.util.Log;

import com.upsoft.sdk.Constant;

import java.io.File;


public class AppCrashHandler extends AppCrashLog{

	
	private static AppCrashHandler mCrashHandler = null;
	
	private AppCrashHandler(){};
	
	public static AppCrashHandler getInstance() {
		
		if(mCrashHandler == null) {
			mCrashHandler = new AppCrashHandler();
		}
		return mCrashHandler;
	} 
	@Override
	public void initParams() {
		// TODO Auto-generated method stub
		Log.e("************", "initParams");
//		AppCrashLog.CACHE_LOG = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"log";
		AppCrashLog.CACHE_LOG = Constant.CACHE_LOGE_PATH;

	}

	@Override
	public void sendCrashLogToServer(File file) {
		// TODO Auto-generated method stub
		
		Log.e("************", "sendCrashLogToServer");
	}

}
