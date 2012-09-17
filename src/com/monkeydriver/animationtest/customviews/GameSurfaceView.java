package com.monkeydriver.animationtest.customviews;

import com.monkeydriver.animationtest.MainThread;
import com.monkeydriver.animationtest.R;
import com.monkeydriver.animationtest.animations.MonkeyAnimation;
import com.monkeydriver.animationtest.globals.Globals;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
	
	public String _avgFps;
	
	private MainThread _thread;
	private MonkeyAnimation _monkey;

	public GameSurfaceView(Context context) {
		super(context);
		// add callback to surface holder to intercept events
		getHolder().addCallback(this);
		
		// create mario and load bitmap
		_monkey = new MonkeyAnimation(BitmapFactory.decodeResource(getResources(), R.drawable.md_run),
																0, 0,		// initial position
																Globals.MONKEY_SPRITE_W,
																Globals.MONKEY_SPRITE_H,	// width and height of sprite
																Globals.MONKEY_ANI_WALK_FPS,
																Globals.MONKEY_ANI_WALK_FRAMES);		// FPS and number of frames in animation
		
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
		
		//TODO: handle touch
		
		return super.onTouchEvent(event);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
	}
	
	public void render(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		_monkey.draw(canvas);
		
		displayFps(canvas, _avgFps);
	}
	
	/**
	 * This is the game update method. It iterates through all the objects
	 * and calls their update method if they have one or calls specific
	 * engine's update method.
	 */
	public void update() {
		_monkey.update(System.currentTimeMillis());
	}
	
	private void displayFps(Canvas canvas, String fps) {
		if(canvas != null && fps != null) {
			Paint paint = new Paint();
			paint.setARGB(255, 255, 255, 255);
			canvas.drawText(fps, this.getWidth()-50, 20, paint);
		}
	}

}
