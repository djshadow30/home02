package com.example.spiderdemo;

import android.content.*;
import android.graphics.*;

public class Missile {

	Bitmap imgMissile;

	// �Ź��� ����� ����ؼ� �̻��� ũ�Ⱑ ����
	int x, y, rad; // ��ǥ ���� ������ x,y���� �Ź��� ���� ���� ������

	boolean isLive = true; // ���� ���� �ʿ�

	int dy; // �̵� ��ȭ��, �Ÿ�,�ӵ� ����

	// Width, Height�� �ʿ� X

	public Missile(Context c, int sp_x, int sp_y, int spSize) {

		x = sp_x; // �Ź��� ��ġ�� �̻����� ��ġ�ϰ� ����
		y = sp_y; // //

		rad = spSize / 6; // �Ź̻������� ũ��� ����

		imgMissile = BitmapFactory.decodeResource(c.getResources(),
				R.drawable.w0);

		imgMissile = Bitmap.createScaledBitmap(imgMissile, rad * 2, rad * 2,
				true);

		dy = rad / 2; // �̵� ��ȭ�� ����

	}
	
	void move(){
		
		y -=  dy; //ȣ��� �� ���� ��ȭ�� ��ŭ �̵�
		
		//ȭ�鿡 ����� ���� �ɱ�

		if(y < -rad){
			isLive = false;
		}
		
		
	}

}
