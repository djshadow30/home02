package com.example.spiderdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Bubble {

	int Width, Height; // ȭ�鿡 �����°� �ֱ� ������ �ʿ�
	int x, y, rad; // ��ǥ�� ũ��
	boolean isLive = true;

	// move�� �ʿ�
	int dx, dy; // ��ȭ��

	// ǳ�� �̹��� ��ȭ�� �ʿ��� ��
	Bitmap imgBubble;
	Bitmap imgBubbles[] = new Bitmap[6];
	int BubbleIdx;
	int loop;

	public Bubble(Context c, int width, int height) {

		Width = width;
		Height = height;

		// ������ ������ �����

		rad = (int) (Math.random() * Height / 20 + Height / 30);

		x = -rad;
		//?
		y = (int) (Math.random() * (Height / 2 - (rad * 2)) + rad);

		dx = (int) (Math.random() * rad/2 + 2);

		int k = (int) (Math.random() * 2) == 0 ? -1 : 1;
		dy = (int) (Math.random() * rad/2) * k;

		imgBubble = BitmapFactory.decodeResource(c.getResources(),
				R.drawable.bubble_1);

		for (int i = 0; i < 4; i++)
			imgBubbles[i] = Bitmap.createScaledBitmap(imgBubble,
					(rad + i * 2) * 2, (rad + i * 2) * 2, true);

		imgBubbles[4] = imgBubbles[2];
		imgBubbles[5] = imgBubbles[1];

		imgBubble = imgBubbles[BubbleIdx];

	}

	void move() {

		loop++;

		if (loop % 3 == 0) {
			loop=0;
			BubbleIdx++;
			imgBubble = imgBubbles[BubbleIdx];
			if (BubbleIdx >= 5) {
				BubbleIdx = 0;
			}
			rad = imgBubble.getWidth() / 2;
		}

		x += dx;
		y += dy;

		if (y < rad) {
			dy = -dy;
			y = rad;
		}
		if (y > Height / 2) {
			dy = -dy;
			y = Height / 2;
		}

		if (x > Width + rad) {
			isLive = false;
		}

	}

}
