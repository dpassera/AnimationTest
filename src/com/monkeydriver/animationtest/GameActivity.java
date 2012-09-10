package com.monkeydriver.animationtest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.monkeydriver.animationtest.customviews.GameSurfaceView;

public class GameActivity extends Activity {
	
	private static final String LOG_TAG = "log_GameActivity";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameSurfaceView(this));
    }
    
    @Override
    protected void onDestroy() {
    	Log.d(LOG_TAG, "# onDestroy");
    	super.onDestroy();
    }
    
    @Override
    protected void onStop() {
    	Log.d(LOG_TAG, "# onStop");
    	super.onStop();
    }
}
