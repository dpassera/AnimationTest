package com.monkeydriver.animationtest.animations;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class MarioAnimation {

	private static final String LOG_TAG = "log_MarioAnimation";
	
	private Bitmap _bitmap;			// the animation sequence
	private Rect _sourceRect;		// the rectangle to be drawn from the animation bitmap
	private int _frameNr;			// the number of frames in the animation
	private int _currentFrame;		// the current frame
	private long _frameTicker;		// the time of the last frame update
	private int _framePeriod;		// milliseconds between each frame (1000/fps)
	
	private int _spriteWidth;		// the width of the sprite to calculate the cutout rectangle
	private int _spriteHeight;		// the height of the sprite
	
	private int _x;					// the X coordinate of the object (top left of the image)
	private int _y;					// the Y coordinate of the object (top left of the image)
	
	public MarioAnimation(Bitmap bitmap,
							int x,
							int y,
							int width,
							int height,
							int fps,
							int frameCount) {
		_bitmap = bitmap;
		_x = x;
		_currentFrame = 0;
		_frameNr = frameCount;
		_spriteWidth = bitmap.getWidth();
		_spriteHeight = bitmap.getHeight();
		_sourceRect = new Rect(0, 0, _spriteWidth, _spriteHeight);
		_framePeriod = 1000 / fps;
		_frameTicker = 01;
	}
	
	public void update(long gameTime) {
		if(gameTime > _frameTicker + _framePeriod) {
			_frameTicker = gameTime;
			// increment the frame
			_currentFrame ++;
			if(_currentFrame >= _frameNr) {
				// reset the frame
				_currentFrame = 0;
			}
		}
		
		// define the rectangle to cut out sprite
		_sourceRect.left = _currentFrame * _spriteWidth;
		_sourceRect.right = _sourceRect.left + _spriteWidth;
	}
	
}
