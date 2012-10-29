package jp.eclipcebook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
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
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

	private String path = "mydata.txt"; //file保存
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE); //タイトルバー非表示
		setTitle("プレイヤー画面");
		setContentView(R.layout.main);
		MediaPlayer bgm1 = MediaPlayer.create(this, R.raw.ikusei_gamen); //ゲーム音楽
		bgm1.start(); //BGMスタート
		doLoad(); //セーブデータをロード
		
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

/******** 実行ボタンの動作 ***********/
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
	public void onWindowFocusChanged(boolean hasFocus) { //アプリを立ち上げた時からbasicのアニメを開始
	 super.onWindowFocusChanged(hasFocus);
	 
	 ImageView img = (ImageView)findViewById(R.id.imageView1);
	 // AnimationDrawableのXMLリソースを指定
	 img.setBackgroundResource(R.drawable.default_position);
	 
	 // AnimationDrawableを取得
	 AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
	 
	 // アニメーションの開始
	 frameAnimation.start();
	}
	
	public final class CommandExecutor implements Runnable { 
		private final Handler handler;

		private CommandExecutor(Handler handler) {
			this.handler = handler;
		}

		public void run() {
			TextView editText1 = (TextView) findViewById(R.id.editText1);
			ImageView image1 = (ImageView)findViewById(R.id.imageView1);

			String str = editText1.getText().toString();
			String[] commands = str.split("\n"); // 1行毎に配列に格納
			List<String> connectCommands = new ArrayList<String>();
			for(int i=0; i<commands.length; i++) {
				connectCommands.add(commands[i]);
			}
			List<String> expandedCommands = new ArrayList<String>();
			moveArray(connectCommands, expandedCommands);
			expandedCommands.add("\n");
			
			AnswerCheck answer = new AnswerCheck();
			answer.compare(expandedCommands); //答えの配列とプレイヤーの配列を比較

			Runnable runnable = new StringParser(image1, expandedCommands, 1);

			for (int i = 0; i < expandedCommands.size(); i++) { /* 解析&実行 */
				
				handler.post(runnable); /* 光らせる */
				
				try { /* 1秒待機 */
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			Log.d("デバッグ", "AnswerCheck:" + answer.show()); //正解、不正解の表示
		}

		private void moveArray(List<String> connectCommands, List<String> expandedCommands) {
			// TODO Auto-generated method stub
			Stack<Integer> loopStack = new Stack<Integer>();
			Stack<Integer> loopCountStack = new Stack<Integer>();
			for (int i = 0; i < connectCommands.size(); i++) {
				if(connectCommands.get(i) == null)
					continue;
				else if (connectCommands.get(i).contains("loop")) { //loopがある
					loopStack.push(i);
					loopCountStack.push(readCount(connectCommands.get(i)));
				}
				else if(connectCommands.get(i).contains("ここまで")) { //kokoがある
					int loopPosition = loopStack.pop();
					int loopCount = loopCountStack.pop();
					connectCommands.remove(i);
					connectCommands.remove(loopPosition);
					makeLoop(connectCommands, loopPosition, i-2, loopCount);
					i = loopPosition - 1;
				}
				else if(loopStack.empty()){ //スタックが空
					expandedCommands.add(connectCommands.get(i));
				}
			}
		}
		
		private void makeLoop(List<String> connectCommands, int firstIndex, int lastIndex, int count) {
			String[] str = new String[lastIndex - firstIndex + 1];
			for(int i = firstIndex; i <= lastIndex; i++) {
				str[i-firstIndex] = connectCommands.get(i);
			}
			for(int i = 0; i < count-1; i++) { //3回繰り返しなら、i < 2 (1回分＋2回分追加)
				for(int j = str.length-1; j >= 0; j--)  {
					connectCommands.add(firstIndex, str[j]);
				}
			}
		}
		
		private int readCount(String loopCount) {
			Pattern p = Pattern.compile("[0-9]");
			Matcher m = p.matcher(loopCount);
			if(!m.find()) {
				return 0;   //0回繰り返し
			}else {
				int startIndex = m.start();
				int countNumber = Integer.parseInt(loopCount.substring(startIndex));
				return countNumber;
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
		EditText editText1 = (EditText)this.findViewById(R.id.editText1);
		Editable str = editText1.getText();
		FileOutputStream output = null;
		try {
			output = this.openFileOutput(path, Context.MODE_WORLD_READABLE);
			output.write(str.toString().getBytes());
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
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
		EditText editText1 = (EditText)this.findViewById(R.id.editText1);
		FileInputStream input = null;
		try {
			input = this.openFileInput(path);
			byte[] buffer = new byte[1000];
			input.read(buffer);
			String s = new String(buffer).trim();
			editText1.setText(s);
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
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
	
	private void changePartnerScreen() { //お手本画面へ遷移
		Intent intent = new Intent(this,jp.eclipcebook.partnerActivity.class);
		this.startActivity(intent);
	}
	
}
