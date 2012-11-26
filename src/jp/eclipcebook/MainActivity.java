// Todoリスト
/* キーボードを出した時の画像潰れ解消法　http://d.hatena.ne.jp/Superdry/20110715/1310754502 */
/* フラグメントによるTabの実装 */
/* 絵文字の実装 */

package jp.eclipcebook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private String path = "mydata.txt"; // file保存
	private String lesson;
	private String message;
	private ImageInEdit mImageEdit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("プレイヤー画面");
		setContentView(R.layout.main);
		
		mImageEdit = (ImageInEdit)findViewById(R.id.imageInEdit);

		/********** 音楽 **************/
		MediaPlayer bgm1 = MediaPlayer.create(this, R.raw.ikusei_gamen); // ゲーム音楽
		bgm1.start(); // BGMスタート
		doLoad(); // セーブデータをロード

		/********** Lesson data　の 取得 **************/
		Intent intent = getIntent();
		lesson = intent.getStringExtra("lesson");
		message = intent.getStringExtra("message");

		Button btn5 = (Button) this.findViewById(R.id.button5);
		btn5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				final Handler handler = new Handler();
				Thread trd = new Thread(new CommandExecutor(handler));
				trd.start();
			}
		});
	}

	/*
	 * @Override public void onWindowFocusChanged(boolean hasFocus) { //
	 * アプリを立ち上げた時からbasicのアニメを開始 super.onWindowFocusChanged(hasFocus);
	 * 
	 * ImageView img = (ImageView) findViewById(R.id.imageView1); //
	 * AnimationDrawableのXMLリソースを指定
	 * img.setBackgroundResource(R.drawable.default_position);
	 * 
	 * // AnimationDrawableを取得 AnimationDrawable frameAnimation =
	 * (AnimationDrawable) img .getBackground();
	 * 
	 * // アニメーションの開始 frameAnimation.start(); }
	 */

	/******************** 構文解析＆実行 *************************/
	public final class CommandExecutor implements Runnable {
		private final Handler handler;

		private CommandExecutor(Handler handler) {
			this.handler = handler;
		}

		public void run() {
			TextView editText1 = (TextView) findViewById(R.id.editText1);
			ImageView leftHand1 = (ImageView) findViewById(R.id.playerLeftHand1);
			ImageView leftHand2 = (ImageView) findViewById(R.id.playerLeftHand2);
			ImageView leftHand3 = (ImageView) findViewById(R.id.playerLeftHand3);
			ImageView rightHand1 = (ImageView) findViewById(R.id.playerRightHand1);
			ImageView rightHand2 = (ImageView) findViewById(R.id.playerRightHand2);
			ImageView rightHand3 = (ImageView) findViewById(R.id.playerRightHand3);
			ImageView basic = (ImageView) findViewById(R.id.playerBasic);
			ImageView leftFoot1 = (ImageView) findViewById(R.id.playerLeftFoot1);
			ImageView leftFoot2 = (ImageView) findViewById(R.id.playerLeftFoot2);
			ImageView leftFoot3 = (ImageView) findViewById(R.id.playerLeftFoot3);
			ImageView rightFoot1 = (ImageView) findViewById(R.id.playerRightFoot1);
			ImageView rightFoot2 = (ImageView) findViewById(R.id.playerRightFoot2);
			ImageView rightFoot3 = (ImageView) findViewById(R.id.playerRightFoot3);

			String commandsText = editText1.getText().toString(); // 1行ずつ配列に収納

			List<String> commands = StringCommandParser.parse(commandsText);

			executeCommands(leftHand1, leftHand2, leftHand3, rightHand1,
					rightHand2, rightHand3, basic, leftFoot1, leftFoot2,
					leftFoot3, rightFoot1, rightFoot2, rightFoot3, commands);

		}

		private void executeCommands(ImageView lh1, ImageView lh2,
				ImageView lh3, ImageView rh1, ImageView rh2, ImageView rh3,
				ImageView basic, ImageView lf1, ImageView lf2, ImageView lf3,
				ImageView rf1, ImageView rf2, ImageView rf3,
				List<String> expandedCommands) {
			Runnable runnable = new StringCommandExecutor(lh1, lh2, lh3, rh1,
					rh2, rh3, basic, lf1, lf2, lf3, rf1, rf2, rf3,
					expandedCommands);
			for (int i = 0; i < expandedCommands.size(); i++) { /* 解析&実行 */
				handler.post(runnable); /* 光らせる */

				try { /* 1秒待機 */
					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				handler.post(runnable);

				try { /* 1秒待機 */
					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/******************** ボタン(絵文字)の処理 *************************/
	public void doActionLeftHandUp(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("左腕を上げる");

		mImageEdit .insertResourceImage(MainActivity.this, R.drawable.basic_piyo);
	}

	public void doActionLeftHandDown(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("左腕を下げる");
	}

	public void doActionRightHandUp(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("右腕を上げる");
	}

	public void doActionRightHandDown(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("右腕を下げる");
	}

	public void doActionLeftFootUp(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("左足を上げる");
	}

	public void doActionLeftFootDown(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("左足を下げる");
	}

	public void doActionRightFootUp(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("右足を上げる");
	}

	public void doActionRightFootDown(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("右足を下げる");
	}

	public void doActionJump(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("ジャンプする");
	}

	public void doActionEnter(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("\n");
	}

	public void doActionLoop(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("loop");
	}

	public void doActionKoko(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("ここまで");
	}

	/******************** ファイル保存 doSave(View view) *************************/
	@SuppressLint("WorldReadableFiles")
	public void doSave() {
		EditText editText1 = (EditText) this.findViewById(R.id.editText1);
		Editable str = editText1.getText();
		FileOutputStream output = null;
		try {
			output = this.openFileOutput(path, Context.MODE_WORLD_READABLE);
			output.write(str.toString().getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/******************** ファイルロード doLoad(View view) *************************/
	public void doLoad() {
		EditText editText1 = (EditText) this.findViewById(R.id.editText1);
		FileInputStream input = null;
		try {
			input = this.openFileInput(path);
			byte[] buffer = new byte[1000];
			input.read(buffer);
			String s = new String(buffer).trim();
			editText1.setText(s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/******************** メニュー作成 *************************/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		super.onCreateOptionsMenu(menu);
		MenuItem item1 = menu.add("実行");
		MenuItem item2 = menu.add("クリア");
		MenuItem item3 = menu.add("お手本");

		item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				changeActionScreen();
				return false;
			}
		});
		item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				TextView editText1 = (TextView) findViewById(R.id.editText1);
				editText1.getEditableText().clear();
				return false;
			}
		});
		item3.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				doSave();
				changePartnerScreen();
				return false;
			}
		});
		return true;
	}

	/************************* インテント（画面遷移） *****************************/
	private void changeActionScreen() {
		Intent intent = new Intent(this, jp.eclipcebook.ActionActivity.class);
		TextView editText1 = (TextView) findViewById(R.id.editText1);
		intent.putExtra("text_data", editText1.getText().toString());
		intent.putExtra("lesson", lesson);
		this.startActivity(intent);
	}

	private void changePartnerScreen() { // お手本画面へ遷移
		Intent intent = new Intent(this, jp.eclipcebook.PartnerActivity.class);
		intent.putExtra("lesson", lesson);
		intent.putExtra("message", message);
		this.startActivity(intent);
	}

}
