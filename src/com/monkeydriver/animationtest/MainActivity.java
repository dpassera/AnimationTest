package com.monkeydriver.animationtest;

import com.monkeydriver.animationtest.customviews.GameSurfaceView;

/**
 * Simple drawing and animation tests based on tutorial from obviam.net,
 * with addition of GCM interface test.
 */

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;

public class MainActivity extends Activity {
	
	private static final String LOG_TAG = "log_MainActivity";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		launchGameActivity();
	}
	
	private void launchGameActivity() {
		Intent gameIntent = new Intent(this, GameActivity.class);
		startActivity(gameIntent);
	}
}
