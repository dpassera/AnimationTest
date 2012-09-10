package com.monkeydriver.animationtest.customviews;

import com.monkeydriver.animationtest.MainThread;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Surface on which objects are drawn.
 * Receives and handles touch events.
 * @author dantepassera
 *
 */

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
	
	private static final String LOG_TAG = "log_GameSurfaceView";
	
	private MainThread _thread;

	public GameSurfaceView(Context context) {
		super(context);
		// add callback to surface holder to intercept events
		getHolder().addCallback(this);
		
		// create the game loop thread
		_thread = new MainThread(getHolder(), this);
		
		// make the GameView focusable, so it can handle events
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// the surface is now created, so we can safely start the game loop
		_thread._isRunning = true;
		_thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(LOG_TAG, "!! surface is being destroyed");
		// tell the thread to shut down and wait for it to finish
		// this is a clean shutdown
		
		boolean doRetry = true;
		while(doRetry) {
			try {
				_thread.join();
				doRetry = false;
			} catch(InterruptedException e) {
				// try again shutting down the thread
			}
		}
		
		Log.d(LOG_TAG, "!! thread was shut down cleanly");
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d(LOG_TAG, "@ event.getAction() = " + event.getAction());
		
		return super.onTouchEvent(event);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
	}

}
