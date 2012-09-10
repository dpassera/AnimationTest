package com.monkeydriver.animationtest;

import com.monkeydriver.animationtest.customviews.GameSurfaceView;

/**
 * Simple drawing and animation tests based on tutorial from obviam.net,
 * with addition of GCM interface test.
 */

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;

public class MainActivity extends Activity {
	
	private static final String LOG_TAG = "log_MainActivity";

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
    
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_layout, menu);
        return true;
    }
    */
}
