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
import android.widget.TextView;

public class ActionActivity extends Activity {

	private String path = "mydata2.txt";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("実行画面");
		setContentView(R.layout.action_screen);
		//doLoad();
		
		TextView playerEditText = (TextView)findViewById(R.id.editTextActionScreen1);
		TextView partnerEditText = (TextView)findViewById(R.id.editTextActionScreen2);
		Intent intent = getIntent();
		String playerCommandsText = intent.getStringExtra("text_data");
		String partnerCommandsText = intent.getStringExtra("lesson");
		playerEditText.setText(playerCommandsText);
		partnerEditText.setText(partnerCommandsText);
		
		Button btn1 = (Button) this.findViewById(R.id.button1);
		btn1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				final Handler handler = new Handler();
				Thread trd = new Thread(new CommandExecutor(handler));
				trd.start();
			}
		});
		
		
	}
	
	private final class CommandExecutor implements Runnable {
		private final Handler handler;

		private CommandExecutor(Handler handler) {
			this.handler = handler;
		}

		public void run() {
			ImageView playerImage1 = (ImageView)findViewById(R.id.playerLeftHand1);
			ImageView playerImage2 = (ImageView)findViewById(R.id.playerLeftHand2);
			ImageView playerImage3 = (ImageView)findViewById(R.id.playerLeftHand3);
			ImageView playerImage4 = (ImageView)findViewById(R.id.playerRightHand1);
			ImageView playerImage5 = (ImageView)findViewById(R.id.playerRightHand2);
			ImageView playerImage6 = (ImageView)findViewById(R.id.playerRightHand3);
			ImageView playerImage7 = (ImageView)findViewById(R.id.playerBasic);
			ImageView playerImage8 = (ImageView)findViewById(R.id.playerLeftFoot1);
			ImageView playerImage9 = (ImageView)findViewById(R.id.playerLeftFoot2);
			ImageView playerImage10 = (ImageView)findViewById(R.id.playerLeftFoot3);
			ImageView playerImage11 = (ImageView)findViewById(R.id.playerRightFoot1);
			ImageView playerImage12 = (ImageView)findViewById(R.id.playerRightFoot2);
			ImageView playerImage13 = (ImageView)findViewById(R.id.playerRightFoot3);
			
			ImageView partnerImage1 = (ImageView)findViewById(R.id.partnerLeftHand1);
			ImageView partnerImage2 = (ImageView)findViewById(R.id.partnerLeftHand2);
			ImageView partnerImage3 = (ImageView)findViewById(R.id.partnerLeftHand3);
			ImageView partnerImage4 = (ImageView)findViewById(R.id.partnerRightHand1);
			ImageView partnerImage5 = (ImageView)findViewById(R.id.partnerRightHand2);
			ImageView partnerImage6 = (ImageView)findViewById(R.id.partnerRightHand3);
			ImageView partnerImage7 = (ImageView)findViewById(R.id.partnerBasic);
			ImageView partnerImage8 = (ImageView)findViewById(R.id.partnerLeftFoot1);
			ImageView partnerImage9 = (ImageView)findViewById(R.id.partnerLeftFoot2);
			ImageView partnerImage10 = (ImageView)findViewById(R.id.partnerLeftFoot3);
			ImageView partnerImage11 = (ImageView)findViewById(R.id.partnerRightFoot1);
			ImageView partnerImage12 = (ImageView)findViewById(R.id.partnerRightFoot2);
			ImageView partnerImage13 = (ImageView)findViewById(R.id.partnerRightFoot3);
			
			TextView playerEditText = (TextView)findViewById(R.id.editTextActionScreen1);
			TextView partnerEditText = (TextView) findViewById(R.id.editTextActionScreen2);
			String playerCommandsText = playerEditText.getText().toString();
			String partnerCommandsText = partnerEditText.getText().toString();
			
			List<String> playerCommands = StringCommandParser.parse(playerCommandsText);
			List<String> partnerCommands = StringCommandParser.parse(partnerCommandsText);

			Runnable playerRunnable = new StringCommandExecutor(playerImage1,playerImage2,playerImage3,playerImage4,playerImage5,playerImage6,playerImage7, playerImage8,playerImage9,playerImage10,playerImage11,playerImage12,playerImage13,playerCommands);
			Runnable partnerRunnable = new StringCommandExecutor(partnerImage1,partnerImage2,partnerImage3,partnerImage4,partnerImage5,partnerImage6,partnerImage7, partnerImage8,partnerImage9,partnerImage10,partnerImage11,partnerImage12,partnerImage13,partnerCommands);

			AnswerCheck answer = new AnswerCheck(playerCommands, partnerCommands);
					
			for (int i = 0; i < Math.max(playerCommands.size(), partnerCommands.size()); i++) { /* 解析&実行 */
				
				if(i < playerCommands.size() ) {
					handler.post(playerRunnable); /* 光らせる */
				}
				if(i < partnerCommands.size()) {
					handler.post(partnerRunnable);
				}
				
				try { /* 1秒待機 */
					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				if(i < playerCommands.size() ) {
					handler.post(playerRunnable); /* 光らせる */
				}
				if(i < partnerCommands.size()) {
					handler.post(partnerRunnable);
				}
				
				try { /* 1秒待機 */
					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			answer.compare(); // 答えの配列とプレイヤーの配列を比較
			Log.d("デバッグ", "AnswerCheck:" + answer.show()); // 正解、不正解の表示
			
			/*AlertDialog.Builder builder = new AlertDialog.Builder(ActionActivity.this);
            builder.setMessage("hoge");
            builder.show();*/ //ダイアログの生成でどうしてもエラーが出てしまう

		}
	}
	
	@SuppressLint("WorldReadableFiles")
	public void doSave() {
		EditText editText2 = (EditText)this.findViewById(R.id.editTextActionScreen2);
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
		EditText editText2 = (EditText)this.findViewById(R.id.editTextActionScreen2);
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
