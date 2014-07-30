package com.example.spiderdemo;

import android.content.*;
import android.graphics.*;

//하나의 공장(int를 받아서  활자를 만들어서 Image로 찍어 보내는 공장)으로 생각
public class ImgScore {

	Bitmap img;
	int x, y;
	int w, h; // 공간이 생성해낸 전체 이미지의 크기와 너비

	int life = 20;
	Bitmap fonts[] = new Bitmap[10];

	// 폰트 사이즈, 좌표
	public ImgScore(Context c, int size, int x, int y, int score) {
		this.x = x;
		this.y = y;

		for (int i = 0; i < 10; i++) {

			fonts[i] = BitmapFactory.decodeResource(c.getResources(),
					R.drawable.f0 + i);
			fonts[i] = Bitmap.createScaledBitmap(fonts[i], size, size, true);

		}

		makeImage(score);

	}

	// 점수가 한번 만들어지고 계속 만들어지므로 생성자에서
	// 부르고 다른 클래스에 부르게 만들게 하기 위해
	void makeImage(int score) {

		// 생성자에 적기(단, 이미지 생성한 후)
		int f_w = fonts[0].getWidth();
		int f_h = fonts[0].getHeight();

		// 숫자를 문자열로 바꾸기( 자기 자리수를 알수 있음)
		String s = score + "";
		int len = s.length();

		// 빈 비트맵 만들기
		// 원하는 사이즈로 비트맵 만드는 것(2번째 createBitmap:width, height, config)
		// config: 색 품질정보(32비트, 16비트) 색칠 할 경우
		// 이미지일 경우 비트맵 이미지의 config를 불러 오면됨
		img = Bitmap.createBitmap(f_w * len, f_h, fonts[0].getConfig());
		/*
		 * Canvas canvas = new Canvas(); canvas.setBitmap(img);
		 */

		// 캔버스의 도화지는 비트맵 이미지로 만듬
		Canvas canvas = new Canvas(img);

		// 이미지가 그려짐
		for (int i = 0; i < len; i++) {
			// 아스키코드로 반환 아스키코드48=0
			int n = s.charAt(i) - 48;
			canvas.drawBitmap(fonts[n], f_w * i, 0, null);
		}

		// 도화지의 크기의 반
		w = img.getWidth() / 2;
		h = img.getHeight() / 2;

	}

	boolean move() {

		y -= (h / 3);

		life--;
		if (life <= 0)
			return true;

		return false;
	}

}
