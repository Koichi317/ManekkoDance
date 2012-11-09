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
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

	private String path = "mydata.txt"; // file保存

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("プレイヤー画面");
		setContentView(R.layout.main);
		MediaPlayer bgm1 = MediaPlayer.create(this, R.raw.ikusei_gamen); // ゲーム音楽
		bgm1.start(); // BGMスタート
		doLoad(); // セーブデータをロード

		/******* tabの作成 **********/
		TabHost tabhost = this.getTabHost();
		TabHost.TabSpec spec1 = tabhost.newTabSpec("腕");
		spec1.setIndicator("腕");
		spec1.setContent(R.id.tab1);
		tabhost.addTab(spec1);
		TabHost.TabSpec spec2 = tabhost.newTabSpec("足");
		spec2.setIndicator("足");
		spec2.setContent(R.id.tab2);
		tabhost.addTab(spec2);
		TabHost.TabSpec spec3 = tabhost.newTabSpec("飛ぶ");
		spec3.setIndicator("飛ぶ");
		spec3.setContent(R.id.tab3);
		tabhost.addTab(spec3);
		TabHost.TabSpec spec4 = tabhost.newTabSpec("表情");
		spec4.setIndicator("表情");
		spec4.setContent(R.id.tab4);
		tabhost.addTab(spec4);
		TabHost.TabSpec spec5 = tabhost.newTabSpec("その他");
		spec5.setIndicator("その他");
		spec5.setContent(R.id.tab5);
		tabhost.addTab(spec5);

		Button btn5 = (Button) this.findViewById(R.id.button5);
		btn5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				final Handler handler = new Handler();
				Thread trd = new Thread(new CommandExecutor(handler));
				trd.start();
			}
		});
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) { // アプリを立ち上げた時からbasicのアニメを開始
		super.onWindowFocusChanged(hasFocus);

		ImageView img = (ImageView) findViewById(R.id.imageView1);
		// AnimationDrawableのXMLリソースを指定
		img.setBackgroundResource(R.drawable.default_position);

		// AnimationDrawableを取得
		AnimationDrawable frameAnimation = (AnimationDrawable) img
				.getBackground();

		// アニメーションの開始
		frameAnimation.start();
	}

	/******************** 構文解析＆実行 *************************/
	public final class CommandExecutor implements Runnable {
		private final Handler handler;

		private CommandExecutor(Handler handler) {
			this.handler = handler;
		}

		public void run() {
			TextView editText1 = (TextView) findViewById(R.id.editText1);
			ImageView image1 = (ImageView) findViewById(R.id.imageView1);
			String commandsText = editText1.getText().toString();

			List<String> commands = StringCommandParser.parse(commandsText);

			executeCommands(image1, commands);

			AnswerCheck answer = new AnswerCheck();
			answer.compare(commands); // 答えの配列とプレイヤーの配列を比較
			Log.d("デバッグ", "AnswerCheck:" + answer.show()); // 正解、不正解の表示
		}

		private void executeCommands(ImageView image1,
				List<String> expandedCommands) {
			Runnable runnable = new StringCommandExecutor(image1,
					expandedCommands);
			for (int i = 0; i < expandedCommands.size(); i++) { /* 解析&実行 */
				handler.post(runnable); /* 光らせる */

				try { /* 1秒待機 */
					Thread.sleep(500);
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
		TextView editText1 = (TextView) findViewById(R.id.editText1);
		Editable str = editText1.getEditableText();
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, str);
		this.startActivity(intent);
	}

	private void changePartnerScreen() { // お手本画面へ遷移
		Intent intent = new Intent(this, jp.eclipcebook.PartnerActivity.class);
		this.startActivity(intent);
	}

}
