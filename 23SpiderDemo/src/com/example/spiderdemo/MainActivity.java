package com.example.spiderdemo;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	public static final int GAME_STOP = 1;
	public static final int GAME_PAUSE = 2;
	public static final int GAME_RESUME = 3;
	public static final int GAME_RESTART = 4;

	GameView gameview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		gameview = (GameView) findViewById(R.id.gameView);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(0, GAME_STOP, 0, "����");
		menu.add(0, GAME_PAUSE, 0, "�Ͻ� ����");
		menu.add(0, GAME_RESUME, 0, "�ٽ� ����");
		menu.add(0, GAME_RESTART, 0, "�ʱ�ȭ");

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();

		switch (id) {
		case GAME_STOP:

			gameview.pauseGame();
			showDialog(GAME_STOP);

			break;
		case GAME_PAUSE:

			gameview.pauseGame();

			break;
		case GAME_RESUME:

			gameview.resumeGame();

			break;
		case GAME_RESTART:

			gameview.pauseGame();
			showDialog(GAME_RESTART);

			break;

		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected Dialog onCreateDialog(int id) {

		Dialog dialog = null;
		
		switch (id) {

		case GAME_STOP:
			 dialog =

			new AlertDialog.Builder(this).setTitle("Ȯ��").setMessage("�����Ͻʴϱ�?")
					.setPositiveButton("��", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							gameview.stopGame();
							finish();
						}
					}).setNegativeButton("���", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							gameview.resumeGame();
						}
					}).create();

			break;
			
		case GAME_RESTART:
			dialog =
			
			new AlertDialog.Builder(this).setTitle("Ȯ��").setMessage("�ʱ�ȭ�Ͻðڽ��ϱ�?")
			.setPositiveButton("��", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					gameview.restartGame();
					
				}
			}).setNegativeButton("���", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					gameview.resumeGame();
				}
			}).create();
			
			break;

		}
		return dialog;
	}

	@Override
	public void onBackPressed() {

		gameview.pauseGame();
		
		showDialog(GAME_STOP);
		
	}
	
	
	
}//MainActivity
