package com.example.spiderdemo;

import android.content.*;
import android.graphics.*;

public class Missile {

	Bitmap imgMissile;

	// 거미의 사이즈에 비례해서 미사일 크기가 결정
	int x, y, rad; // 좌표 값과 반지름 x,y값은 거미의 값에 의해 정해짐

	boolean isLive = true; // 생명 변수 필요

	int dy; // 이동 변화량, 거리,속도 조절

	// Width, Height는 필요 X

	public Missile(Context c, int sp_x, int sp_y, int spSize) {

		x = sp_x; // 거미의 위치에 미사일이 위치하게 만듬
		y = sp_y; // //

		rad = spSize / 6; // 거미사이즈의 크기로 지정

		imgMissile = BitmapFactory.decodeResource(c.getResources(),
				R.drawable.w0);

		imgMissile = Bitmap.createScaledBitmap(imgMissile, rad * 2, rad * 2,
				true);

		dy = rad / 2; // 이동 변화량 지정

	}
	
	void move(){
		
		y -=  dy; //호출될 때 마다 변화량 만큼 이동
		
		//화면에 벗어나면 조건 걸기

		if(y < -rad){
			isLive = false;
		}
		
		
	}

}
