package jp.eclipcebook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
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

public class ActionActivity extends Activity {

	String str;
	private String path = "mydata2.txt";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("実行画面");
		setContentView(R.layout.action_screen);
		doLoad();
		Intent intent = this.getIntent();
		if(intent.getAction().equals(Intent.ACTION_SEND)) {
			Bundle bundle = intent.getExtras();
			str = bundle.getCharSequence(Intent.EXTRA_TEXT).toString();
		}
		EditText editText1 = (EditText)this.findViewById(R.id.editText1);
		editText1.append(str);
		Button btn1 = (Button) this.findViewById(R.id.button1);
		btn1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				final Handler handler = new Handler();
				Thread trd = new Thread(new CommandExecutor(handler));
				trd.start();
			}
		});
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
	 super.onWindowFocusChanged(hasFocus);
	 
	 ImageView img = (ImageView)findViewById(R.id.imageView1);
	 // AnimationDrawableのXMLリソースを指定
	 img.setBackgroundResource(R.drawable.default_position);
	 
	 // AnimationDrawableを取得
	 AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
	 
	 // アニメーションの開始
	 frameAnimation.start();
	}
	
	private final class CommandExecutor implements Runnable {
		private final Handler handler;

		private CommandExecutor(Handler handler) {
			this.handler = handler;
		}

		public void run() {
			ImageView image1 = (ImageView)findViewById(R.id.imageView1);
			ImageView image2 = (ImageView)findViewById(R.id.imageView2);
			TextView editText2 = (TextView) findViewById(R.id.editText2);
			String str2 = editText2.getText().toString();
			String[] commands = str.split("\n"); // 1行毎に配列に格納
			String[] commands2 = str2.split("\n");
			List<String> connectCommands = new ArrayList<String>();
			List<String> expandedCommands = new ArrayList<String>();
			List<String> connectCommands2 = new ArrayList<String>();
			List<String> expandedCommands2 = new ArrayList<String>();
			
			
			for(int i=0; i<commands.length; i++) {
				connectCommands.add(commands[i]);
			}
			for(int i=0; i<commands2.length; i++) {
				connectCommands2.add(commands2[i]);
			}
			moveArray(connectCommands, expandedCommands);
			expandedCommands.add("\n ");
			moveArray(connectCommands2, expandedCommands2);
			expandedCommands2.add("\n ");

			Runnable runnable = new StringParser(image1, expandedCommands, 1);
			Runnable runnable2 = new StringParser(image2, expandedCommands2, 2);

			for (int i = 0; i < expandedCommands.size(); i++) { /* 解析&実行 */
				handler.post(runnable); /* 光らせる */
				handler.post(runnable2);
				try { /* 1秒待機 */
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
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
	
	@SuppressLint("WorldReadableFiles")
	public void doSave() {
		EditText editText2 = (EditText)this.findViewById(R.id.editText2);
		Editable str = editText2.getText();
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
	
	public void doLoad() { 
		EditText editText2 = (EditText)this.findViewById(R.id.editText2);
		FileInputStream input = null;
		try {
			input = this.openFileInput(path);
			byte[] buffer = new byte[1000];
			input.read(buffer);
			String s = new String(buffer).trim();
			editText2.setText(s);
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

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		super.onCreateOptionsMenu(menu);
		MenuItem item1 = menu.add("実行");
		MenuItem item2 = menu.add("戻る");
		
		item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				
				return false;
			}
		});
		item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				doSave();
				changeMainScreen();
				return false;
			}
		});
		return true;
	}
	
	private void changeMainScreen() {
		Intent intent = new Intent(getApplication(),jp.eclipcebook.MainActivity.class);
		this.startActivity(intent);
	}
	
	
}
