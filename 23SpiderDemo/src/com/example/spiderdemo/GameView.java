package com.example.spiderdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Callback {

	Context mContext;
	SurfaceHolder mHolder;
	GameThread mThread;

	int Width, Height;

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);

		mContext = context;
		mHolder = getHolder();
		mHolder.addCallback(this);
	}

	// GameView에서 작성 터치 이벤트
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int action = event.getAction();

		int cl_x = (int) event.getX();
		int cl_y = (int) event.getY();

		switch (action) {

		//처음 눌러야 하니깐 필요
		case MotionEvent.ACTION_DOWN:

			mThread.touchDown(cl_x, cl_y);
			
			break;
			
			
			
			//누르고 움직여야 하므로 필요
		case MotionEvent.ACTION_MOVE:
			
			mThread.touchMove(cl_x, cl_y);
			
			break;

		}

		return true;
	}

	void stopGame() {
		mThread.stopThread();
	}

	void pauseGame() {
		mThread.pauseThread(true);
	}

	void resumeGame() {
		mThread.pauseThread(false);
	}

	void restartGame() {
		mThread.stopThread();
		mThread = null;

		mThread = new GameThread(mContext, mHolder, Width, Height);
		mThread.start();

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

		Width = getWidth();
		Height = getHeight();

		if (mThread == null) {
			mThread = new GameThread(mContext, mHolder, Width, Height);
			mThread.start();
		}

		else {
			mThread.pauseThread(false);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

}
