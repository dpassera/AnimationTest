package com.monkeydriver.animationtest;

import java.text.DecimalFormat;

import com.monkeydriver.animationtest.customviews.GameSurfaceView;

import android.graphics.Canvas;
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
	
	private SurfaceHolder _surfaceHolder;			// surface holder that can access the physical surface
	private GameSurfaceView _gameView;				// the view that handles inputs and draws to the surface

	public boolean _isRunning;						// flag to hold game state
	
	private static final int MAX_FPS = 24;			// desired FPS
	private static final int MAX_FRAME_SKIPS = 5;	// maximum number of frames to be skipped
	private static final int FRAME_PERIOD = 1000 / MAX_FPS;	// the frame period (ms)
	
	/* stats variables */
	private DecimalFormat _df = new DecimalFormat("0.##");	// 2 decimal points
	private static final int STAT_INTERVAL = 1000;	// period to read stats (ms)
	private static final int FPS_HISTORY_NR = 10;	// the last n FPSes
	private long _statusIntervalTimer = 0l;			// the status time counter
	private long _totalFramesSkipped = 0l;			// number of frames skipped since game started
	private long _framesSkippedPerStatCycle = 0l;	// number of frames skipped in a store cycle (1 sec)
	
	private int _frameCountPerStatCycle = 0;		// number of rendered frames in an interval
	private long _totalFrameCount = 0l;				//
	private double _fpsStore[];						// the last FPS values
	private long _statsCount = 0;					// the number of times the stat has been read
	private double _averageFps = 0.0;				// the average FPS since the game started
	
	public MainThread(SurfaceHolder surfaceHolder, GameSurfaceView gameView) {
		super();
		
		_surfaceHolder = surfaceHolder;
		_gameView = gameView;
	}
	
	@Override
	public void run() {
		Canvas canvas;
		
		Log.d(LOG_TAG, "!! starting game loop");
		initTimingElements();
		
		long beginTime;		// the time when the cycle began
		long timeDiff;		// the time it took for the cycle to execute
		int sleepTime;		// ms to sleep (<0 if we're behind)
		int framesSkipped;	// number of frames being skipped
		
		sleepTime = 0;
		
		while(_isRunning) {
			canvas = null;
			
			// try locking the canvas for exclusive pixel editing in the surface
			try {
				canvas = _surfaceHolder.lockCanvas();
				synchronized(_surfaceHolder) {
					beginTime = System.currentTimeMillis();
					framesSkipped = 0;
					_gameView.update();
					_gameView.render(canvas);
					// calculate how long the cycle took
					timeDiff = System.currentTimeMillis() - beginTime;
					// calculate sleep time
					sleepTime = (int)(FRAME_PERIOD - timeDiff);
					
					// check if frame was rendered faster than frame period
					if(sleepTime > 0) {
						try {
							// put thread to sleep for a short period - useful for saving battery
							Thread.sleep(sleepTime);
						} catch(InterruptedException e) { }
					}
					
					// if frame took longer to render than frame period
					while(sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
						// update without rendering
						_gameView.update();
						// add frame period to check if in next frame
						sleepTime += FRAME_PERIOD;
						framesSkipped ++;
					}
					
					// for stats
					_framesSkippedPerStatCycle += framesSkipped;
					storeStats();
				}
			} finally {
				// in case of an exception, the surface is not left in an inconsistent state
				if(canvas != null) {
					_surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}
	
	/**
	 * The statistics - it is called every cycle, it checks if time since last
	 * store is greater than the statistics gathering period (1 sec) and if so
	 * it calculates the FPS for the last period and stores it.
	 * 
	 *  It tracks the number of frames per period. The number of frames since 
	 *  the start of the period are summed up and the calculation takes part
	 *  only if the next period and the frame count is reset to 0.
	 */
	
	private void storeStats() {
		_frameCountPerStatCycle ++;
		_totalFrameCount ++;
		_statusIntervalTimer += FRAME_PERIOD;
		
		if(_statusIntervalTimer >= FRAME_PERIOD) {
			// calculate the actual frames per status check interval
			double actualFPS = (double)(_frameCountPerStatCycle / (STAT_INTERVAL / 1000));
			
			// stores the latest fps in the array
			_fpsStore[(int) _statsCount % FPS_HISTORY_NR] = actualFPS;
			
			_statsCount ++;
			
			double totalFps = 0.0;
			// sum up the stored fps values
			for(int i=0; i<FPS_HISTORY_NR; i++) {
				totalFps += _fpsStore[i];
			}
			
			// obtain the average fps
			if(_statsCount < FPS_HISTORY_NR) {
				_averageFps = totalFps / _statsCount;
			} else {
				_averageFps = totalFps / FPS_HISTORY_NR;
			}
			
			_totalFramesSkipped += _framesSkippedPerStatCycle;
			
			_framesSkippedPerStatCycle = 0;
			_statusIntervalTimer = 0;
			_frameCountPerStatCycle = 0;
			
			_gameView._avgFps = "FPS: " + _df.format(_averageFps);
		}
	}
	
	private void initTimingElements() {
		Log.d(LOG_TAG, "# initTimingElements");
		
		_fpsStore = new double[FPS_HISTORY_NR];
		
		for(int i=0; i<FPS_HISTORY_NR; i++) {
			_fpsStore[i] = 0.0;
		}
		
		Log.d(LOG_TAG, "!! timing elements for stats initialized");
	}
	
}
