package com.monkeydriver.animationtest;

import com.monkeydriver.animationtest.customviews.GameSurfaceView;

import android.util.Log;
import android.view.SurfaceHolder;

/**
 * 
 * The game loop.
 * @author dantepassera
 *
 */

public class MainThread extends Thread {

	private static final String LOG_TAG = "log_MainThread";
	
	private SurfaceHolder _surfaceHolder;		// surface holder that can access the physical surface
	private GameSurfaceView _gameView;			// the view that handles inputs and draws to the surface

	public boolean _isRunning;					// flag to hold game state
	
	public MainThread(SurfaceHolder surfaceHolder, GameSurfaceView gameView) {
		super();
		
		_surfaceHolder = surfaceHolder;
		_gameView = gameView;
	}
	
	@Override
	public void run() {
		long tickCount = 0L;
		
		Log.d(LOG_TAG, "!! Starting game loop");
		while(_isRunning) {
			tickCount ++;
			//TODO: update game state
			//TODO: render state to the screen
		}
		Log.d(LOG_TAG, "!! Game loop executed " + tickCount + " times");
	}
	
}
