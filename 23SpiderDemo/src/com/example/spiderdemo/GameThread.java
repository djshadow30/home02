package com.example.spiderdemo;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.View;

public class GameThread extends Thread {

	Context mContext;
	SurfaceHolder mHolder;
	int Width, Height;
	int dx, dy;

	boolean isRun = true;
	boolean isWait = false;

	Bitmap imgBack;
	Bitmap imgSpider;

	int sp_x, sp_y; // 거미의 위치 좌표
	int sp_w, sp_h; // 거미 크기의 반

	int current_x; // 거미의 터치 위치

	long missileTime; // 미사일 생성 제어

	ArrayList<Missile> mMissiles = new ArrayList<Missile>();
	ArrayList<Bubble> mBubbles = new ArrayList<Bubble>();
	ArrayList<SmallBubble> msSmallBubbles = new ArrayList<SmallBubble>();
	ArrayList<ImgScore> mImageScores = new ArrayList<ImgScore>();

	ImgScore imgScore;

	// 초기값 0
	int score;

	public GameThread(Context c, SurfaceHolder holder, int width, int height) {

		mContext = c;
		mHolder = holder;
		Width = width;
		Height = height;

		// size = 폰트 하나의 사이즈
		imgScore = new ImgScore(mContext, Height / 20, Width / 2, 10, score);

		imgBack = BitmapFactory
				.decodeResource(c.getResources(), R.drawable.sky);

		imgBack = Bitmap.createScaledBitmap(imgBack, width, height, true);

		imgSpider = BitmapFactory.decodeResource(c.getResources(),
				R.drawable.spider1);

		// 이걸 만들면 이미지의 크기를 구할 수 있다
		imgSpider = Bitmap.createScaledBitmap(imgSpider, height / 5,
				height / 5, true);

		sp_w = imgSpider.getWidth() / 2;
		sp_h = imgSpider.getHeight() / 2;

		sp_x = Width / 2;
		sp_y = Height - sp_h;

		missileTime = System.currentTimeMillis();

	}

	void touchDown(int cl_x, int cl_y) {

		this.current_x = cl_x;
	}

	void touchMove(int m_x, int m_y) {

		int dx = m_x - current_x;
		sp_x += dx;
		current_x = m_x;

		if (sp_x - sp_w <= 0) {
			sp_x = sp_w;
		}

		if (sp_x >= Width - sp_w) {
			sp_x = Width - sp_w;
		}

	}
	

	void drawAll(Canvas canvas) {

		// 항상 가려짐
		// 배경 그리기
		canvas.drawBitmap(imgBack, 0, 0, null);

		// 버블 그리기
		for (Bubble t : mBubbles) {
			canvas.drawBitmap(t.imgBubble, t.x - t.rad, t.y - t.rad, null);
		}

		// 미사일 그리기
		for (Missile t : mMissiles)
			canvas.drawBitmap(t.imgMissile, t.x - t.rad, t.y - t.rad, null);

		// 거미 그리기
		canvas.drawBitmap(imgSpider, sp_x - sp_w, sp_y - sp_h, null);

		// 파편 그리기
		for (SmallBubble t : msSmallBubbles)
			canvas.drawBitmap(t.img, t.x - t.rad, t.y - t.rad, null);

		// 움직이는 점수 그리기
		for (ImgScore t : mImageScores) {
			canvas.drawBitmap(t.img, t.x - t.w, t.y-t.h, null);
		}

		// 아주 잘 보이게
		imgScore.makeImage(score);
		canvas.drawBitmap(imgScore.img, imgScore.x - imgScore.w, 10, null);

	}

	// 모든 오브젝트를 움직이는 작업을 하는 메소드
	void moveAll() {
		
		// 점수 움직이기(파편이랑 같은)
		for (int i = mImageScores.size() - 1; i >= 0; i--) {
			// msSmallBubbles.get(i).move();
			if (mImageScores.get(i).move()) {
				mImageScores.remove(i);
			}
		}

		// Missile 움직이기
		for (int i = mMissiles.size() - 1; i >= 0; i--) {
			mMissiles.get(i).move();
			if (!mMissiles.get(i).isLive) {

				mMissiles.remove(i);
			}
		}
		// 버블 움직이기
		for (int i = mBubbles.size() - 1; i >= 0; i--) {
			mBubbles.get(i).move();
			// && mBubbles.get(i).x < Width+mBubbles.get(i).rad
			if (!mBubbles.get(i).isLive) {
				// 파편 만들기....
				// 정보가 있는 remove앞에서 만듬
				int bx = mBubbles.get(i).x;
				int by = mBubbles.get(i).y;

				makeSmallBubbles(bx, by);

				// 움직이는 점수 만들기
				int d_rad = mBubbles.get(i).rad;
				mImageScores.add(new ImgScore(mContext, Height / 40, bx, by,
						100 - d_rad));
				score += 100 - d_rad;

				mBubbles.remove(i);
			}
		}
		// 파편 움직이기
		for (int i = msSmallBubbles.size() - 1; i >= 0; i--) {
			// msSmallBubbles.get(i).move();
			if (msSmallBubbles.get(i).move()) {
				msSmallBubbles.remove(i);
			}
		}

	}

	void makeSmallBubbles(int bomb_x, int bomb_y) {

		int n = (int) (Math.random() * 9 + 7); // 7~15개 만들기

		for (int i = 0; i < n; i++) {
			msSmallBubbles.add(new SmallBubble(mContext, bomb_x, bomb_y));
		}

	}

	void makeAll() {

		long thisTime = System.currentTimeMillis();

		// 미사일 만들기
		if (thisTime - missileTime > 100) {
			mMissiles.add(new Missile(mContext, sp_x, sp_y, sp_w));
			missileTime = thisTime;
		}

		// 버블 만들기(확률적으로 나오게 만들기)
		int p = (int) (Math.random() * 5);

		// p==0일 확률은 1/5로 됨
		if (p == 0 && mBubbles.size() < 30) {
			mBubbles.add(new Bubble(mContext, Width, Height));
		}

	}

	void checkCollision() {
		// 미사일과 적군의 충돌

		for (Missile t : mMissiles) {
			for (Bubble bt : mBubbles) {

				if (Math.pow(t.x - bt.x, 2) + Math.pow(t.y - bt.y, 2) < Math
						.pow(t.rad + bt.rad, 2)) {

					bt.isLive = false;
					t.isLive = false;

					return;

				}

			}

		}

	}

	@Override
	public void run() {

		Canvas canvas = null;

		while (isRun) {

			canvas = mHolder.lockCanvas();

			try {

				synchronized (mHolder) {
					// 원하는 작업

					// 순서도 생각하면서 메소드 배치하기
					checkCollision();
					makeAll();
					moveAll();
					drawAll(canvas);
				}

			} finally {

				if (canvas != null) {
					mHolder.unlockCanvasAndPost(canvas);
				}
			}

			synchronized (this) {
				if (isWait) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}// while문

	}// run()

	void stopThread() {

		isRun = false;

		synchronized (this) {
			this.notify(); // pause인 상태가 wait일때 깨워서 while 조건식으로 종료시킴
		}
	}

	void pauseThread(boolean wait) {
		synchronized (this) {
			isWait = wait;
			this.notify(); // ? wait일때 깨울려고
		}

	}

}// GameThread
