package com.example.spiderdemo;

import android.content.*;
import android.graphics.*;

public class SmallBubble {

	Bitmap img;
	int x, y, rad;

	// 불린 변수는 일정 시간 지나면 죽기 때문에 필요없음

	double radian; // 이동 각도
	int speed; // 이동 속도

	int life;

	public SmallBubble(Context c, int bomb_x, int bomb_y) {

		x = bomb_x;
		y = bomb_y;

		rad = (int) (Math.random() * 5 + 2); // 2~6픽셀
		speed = (int) (Math.random() * 6 + 2); // 2~7픽셀
		life = (int) (Math.random() * 31 + 20); // 20~50

		int angle = (int) (Math.random() * 360); // 0~359도

		radian = Math.toRadians(angle); // 각도를 라디안으로

		// 랜덤하게 나올 이미지 (순차적 아디 이름을 이용)
		int n = (int) (Math.random() * 6);

		img = BitmapFactory.decodeResource(c.getResources(), R.drawable.b0 + n);
		img = Bitmap.createScaledBitmap(img, rad * 2, rad * 2, true);

	}

	// 반환값으로 생명 판단
	boolean move() {

		x = (int) (x + Math.cos(radian) * speed);
		y = (int) (y - Math.sin(radian) * speed);

		life--;
		if (life <= 0)
			return true;

		return false;
	}

}
