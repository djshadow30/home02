package com.example.spiderdemo;

import android.content.*;
import android.graphics.*;

public class SmallBubble {

	Bitmap img;
	int x, y, rad;

	// �Ҹ� ������ ���� �ð� ������ �ױ� ������ �ʿ����

	double radian; // �̵� ����
	int speed; // �̵� �ӵ�

	int life;

	public SmallBubble(Context c, int bomb_x, int bomb_y) {

		x = bomb_x;
		y = bomb_y;

		rad = (int) (Math.random() * 5 + 2); // 2~6�ȼ�
		speed = (int) (Math.random() * 6 + 2); // 2~7�ȼ�
		life = (int) (Math.random() * 31 + 20); // 20~50

		int angle = (int) (Math.random() * 360); // 0~359��

		radian = Math.toRadians(angle); // ������ ��������

		// �����ϰ� ���� �̹��� (������ �Ƶ� �̸��� �̿�)
		int n = (int) (Math.random() * 6);

		img = BitmapFactory.decodeResource(c.getResources(), R.drawable.b0 + n);
		img = Bitmap.createScaledBitmap(img, rad * 2, rad * 2, true);

	}

	// ��ȯ������ ���� �Ǵ�
	boolean move() {

		x = (int) (x + Math.cos(radian) * speed);
		y = (int) (y - Math.sin(radian) * speed);

		life--;
		if (life <= 0)
			return true;

		return false;
	}

}
