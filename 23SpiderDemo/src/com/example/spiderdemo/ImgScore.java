package com.example.spiderdemo;

import android.content.*;
import android.graphics.*;

//�ϳ��� ����(int�� �޾Ƽ�  Ȱ�ڸ� ���� Image�� ��� ������ ����)���� ����
public class ImgScore {

	Bitmap img;
	int x, y;
	int w, h; // ������ �����س� ��ü �̹����� ũ��� �ʺ�

	int life = 20;
	Bitmap fonts[] = new Bitmap[10];

	// ��Ʈ ������, ��ǥ
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

	// ������ �ѹ� ��������� ��� ��������Ƿ� �����ڿ���
	// �θ��� �ٸ� Ŭ������ �θ��� ����� �ϱ� ����
	void makeImage(int score) {

		// �����ڿ� ����(��, �̹��� ������ ��)
		int f_w = fonts[0].getWidth();
		int f_h = fonts[0].getHeight();

		// ���ڸ� ���ڿ��� �ٲٱ�( �ڱ� �ڸ����� �˼� ����)
		String s = score + "";
		int len = s.length();

		// �� ��Ʈ�� �����
		// ���ϴ� ������� ��Ʈ�� ����� ��(2��° createBitmap:width, height, config)
		// config: �� ǰ������(32��Ʈ, 16��Ʈ) ��ĥ �� ���
		// �̹����� ��� ��Ʈ�� �̹����� config�� �ҷ� �����
		img = Bitmap.createBitmap(f_w * len, f_h, fonts[0].getConfig());
		/*
		 * Canvas canvas = new Canvas(); canvas.setBitmap(img);
		 */

		// ĵ������ ��ȭ���� ��Ʈ�� �̹����� ����
		Canvas canvas = new Canvas(img);

		// �̹����� �׷���
		for (int i = 0; i < len; i++) {
			// �ƽ�Ű�ڵ�� ��ȯ �ƽ�Ű�ڵ�48=0
			int n = s.charAt(i) - 48;
			canvas.drawBitmap(fonts[n], f_w * i, 0, null);
		}

		// ��ȭ���� ũ���� ��
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
