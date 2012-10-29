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

	private String playerCommandsText;
	private String path = "mydata2.txt";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("実行画面");
		setContentView(R.layout.action_screen);
		doLoad();
		Intent intent = this.getIntent();
		if(intent.getAction().equals(Intent.ACTION_SEND)) {
			Bundle bundle = intent.getExtras();
			playerCommandsText = bundle.getCharSequence(Intent.EXTRA_TEXT).toString();
		}
		EditText editText1 = (EditText)this.findViewById(R.id.editText1);
		editText1.append(playerCommandsText);
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
			ImageView playerImage = (ImageView)findViewById(R.id.imageView1);
			ImageView partnerImage = (ImageView)findViewById(R.id.imageView2);
			TextView partnerEditText = (TextView) findViewById(R.id.editText2);
			String partnerCommandsText = partnerEditText.getText().toString();
			
			List<String> playerCommands = StringCommandParser.parse(playerCommandsText);
			List<String> partnerCommands = StringCommandParser.parse(partnerCommandsText);

			Runnable playerRunnable = new StringCommandExecutor(playerImage, playerCommands);
			Runnable partnerRunnable = new StringCommandExecutor(partnerImage, partnerCommands);

			for (int i = 0; i < playerCommands.size(); i++) { /* 解析&実行 */
				handler.post(playerRunnable); /* 光らせる */
				handler.post(partnerRunnable);
				try { /* 1秒待機 */
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
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
