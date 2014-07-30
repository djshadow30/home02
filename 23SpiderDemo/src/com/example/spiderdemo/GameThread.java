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

	int sp_x, sp_y; // �Ź��� ��ġ ��ǥ
	int sp_w, sp_h; // �Ź� ũ���� ��

	int current_x; // �Ź��� ��ġ ��ġ

	long missileTime; // �̻��� ���� ����

	ArrayList<Missile> mMissiles = new ArrayList<Missile>();
	ArrayList<Bubble> mBubbles = new ArrayList<Bubble>();
	ArrayList<SmallBubble> msSmallBubbles = new ArrayList<SmallBubble>();
	ArrayList<ImgScore> mImageScores = new ArrayList<ImgScore>();

	ImgScore imgScore;

	// �ʱⰪ 0
	int score;

	public GameThread(Context c, SurfaceHolder holder, int width, int height) {

		mContext = c;
		mHolder = holder;
		Width = width;
		Height = height;

		// size = ��Ʈ �ϳ��� ������
		imgScore = new ImgScore(mContext, Height / 20, Width / 2, 10, score);

		imgBack = BitmapFactory
				.decodeResource(c.getResources(), R.drawable.sky);

		imgBack = Bitmap.createScaledBitmap(imgBack, width, height, true);

		imgSpider = BitmapFactory.decodeResource(c.getResources(),
				R.drawable.spider1);

		// �̰� ����� �̹����� ũ�⸦ ���� �� �ִ�
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

		// �׻� ������
		// ��� �׸���
		canvas.drawBitmap(imgBack, 0, 0, null);

		// ���� �׸���
		for (Bubble t : mBubbles) {
			canvas.drawBitmap(t.imgBubble, t.x - t.rad, t.y - t.rad, null);
		}

		// �̻��� �׸���
		for (Missile t : mMissiles)
			canvas.drawBitmap(t.imgMissile, t.x - t.rad, t.y - t.rad, null);

		// �Ź� �׸���
		canvas.drawBitmap(imgSpider, sp_x - sp_w, sp_y - sp_h, null);

		// ���� �׸���
		for (SmallBubble t : msSmallBubbles)
			canvas.drawBitmap(t.img, t.x - t.rad, t.y - t.rad, null);

		// �����̴� ���� �׸���
		for (ImgScore t : mImageScores) {
			canvas.drawBitmap(t.img, t.x - t.w, t.y-t.h, null);
		}

		// ���� �� ���̰�
		imgScore.makeImage(score);
		canvas.drawBitmap(imgScore.img, imgScore.x - imgScore.w, 10, null);

	}

	// ��� ������Ʈ�� �����̴� �۾��� �ϴ� �޼ҵ�
	void moveAll() {
		
		// ���� �����̱�(�����̶� ����)
		for (int i = mImageScores.size() - 1; i >= 0; i--) {
			// msSmallBubbles.get(i).move();
			if (mImageScores.get(i).move()) {
				mImageScores.remove(i);
			}
		}

		// Missile �����̱�
		for (int i = mMissiles.size() - 1; i >= 0; i--) {
			mMissiles.get(i).move();
			if (!mMissiles.get(i).isLive) {

				mMissiles.remove(i);
			}
		}
		// ���� �����̱�
		for (int i = mBubbles.size() - 1; i >= 0; i--) {
			mBubbles.get(i).move();
			// && mBubbles.get(i).x < Width+mBubbles.get(i).rad
			if (!mBubbles.get(i).isLive) {
				// ���� �����....
				// ������ �ִ� remove�տ��� ����
				int bx = mBubbles.get(i).x;
				int by = mBubbles.get(i).y;

				makeSmallBubbles(bx, by);

				// �����̴� ���� �����
				int d_rad = mBubbles.get(i).rad;
				mImageScores.add(new ImgScore(mContext, Height / 40, bx, by,
						100 - d_rad));
				score += 100 - d_rad;

				mBubbles.remove(i);
			}
		}
		// ���� �����̱�
		for (int i = msSmallBubbles.size() - 1; i >= 0; i--) {
			// msSmallBubbles.get(i).move();
			if (msSmallBubbles.get(i).move()) {
				msSmallBubbles.remove(i);
			}
		}

	}

	void makeSmallBubbles(int bomb_x, int bomb_y) {

		int n = (int) (Math.random() * 9 + 7); // 7~15�� �����

		for (int i = 0; i < n; i++) {
			msSmallBubbles.add(new SmallBubble(mContext, bomb_x, bomb_y));
		}

	}

	void makeAll() {

		long thisTime = System.currentTimeMillis();

		// �̻��� �����
		if (thisTime - missileTime > 100) {
			mMissiles.add(new Missile(mContext, sp_x, sp_y, sp_w));
			missileTime = thisTime;
		}

		// ���� �����(Ȯ�������� ������ �����)
		int p = (int) (Math.random() * 5);

		// p==0�� Ȯ���� 1/5�� ��
		if (p == 0 && mBubbles.size() < 30) {
			mBubbles.add(new Bubble(mContext, Width, Height));
		}

	}

	void checkCollision() {
		// �̻��ϰ� ������ �浹

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
					// ���ϴ� �۾�

					// ������ �����ϸ鼭 �޼ҵ� ��ġ�ϱ�
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

		}// while��

	}// run()

	void stopThread() {

		isRun = false;

		synchronized (this) {
			this.notify(); // pause�� ���°� wait�϶� ������ while ���ǽ����� �����Ŵ
		}
	}

	void pauseThread(boolean wait) {
		synchronized (this) {
			isWait = wait;
			this.notify(); // ? wait�϶� �������
		}

	}

}// GameThread
