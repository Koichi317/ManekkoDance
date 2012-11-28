package jp.eclipcebook;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PartnerActivity extends Activity {

	// private String path = "mydata2.txt"; // file保存
	private String text_data;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE); //タイトルバー非表示
		setTitle("お手本画面");
		setContentView(R.layout.partner);
		// doLoad();

		TextView editText1 = (TextView) findViewById(R.id.editText1);
		TextView editText2 = (TextView) findViewById(R.id.editText2);
		ImageView messageImageView1 = (ImageView) findViewById(R.id.imageView2);
		Intent intent = getIntent();
		String data = intent.getStringExtra("lesson");
		String message = intent.getStringExtra("message");
		text_data = intent.getStringExtra("text_data");
		editText1.setText(data);
		editText2.setText(message);
		if (message.equals("lesson1")) {
			messageImageView1.setImageResource(R.drawable.lesson_message1);
		} else if (message.equals("lesson2")) {
			messageImageView1.setImageResource(R.drawable.lesson_message2);
		} else if (message.equals("lesson3")) {
			messageImageView1.setImageResource(R.drawable.lesson_message3);
		} else if (message.equals("lesson4")) {
			messageImageView1.setImageResource(R.drawable.lesson_message4);
		}

		Button btn5 = (Button) this.findViewById(R.id.button5);
		btn5.setOnClickListener(new View.OnClickListener() {
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
			TextView editText1 = (TextView) findViewById(R.id.editText1);
			ImageView leftHand1 = (ImageView) findViewById(R.id.partnerLeftHand1);
			ImageView leftHand2 = (ImageView) findViewById(R.id.partnerLeftHand2);
			ImageView leftHand3 = (ImageView) findViewById(R.id.partnerLeftHand3);
			ImageView rightHand1 = (ImageView) findViewById(R.id.partnerRightHand1);
			ImageView rightHand2 = (ImageView) findViewById(R.id.partnerRightHand2);
			ImageView rightHand3 = (ImageView) findViewById(R.id.partnerRightHand3);
			ImageView basic = (ImageView) findViewById(R.id.partnerBasic);
			ImageView leftFoot1 = (ImageView) findViewById(R.id.partnerLeftFoot1);
			ImageView leftFoot2 = (ImageView) findViewById(R.id.partnerLeftFoot2);
			ImageView leftFoot3 = (ImageView) findViewById(R.id.partnerLeftFoot3);
			ImageView rightFoot1 = (ImageView) findViewById(R.id.partnerRightFoot1);
			ImageView rightFoot2 = (ImageView) findViewById(R.id.partnerRightFoot2);
			ImageView rightFoot3 = (ImageView) findViewById(R.id.partnerRightFoot3);
			String commandsText = editText1.getText().toString();

			List<String> commands = StringCommandParser.parse(commandsText);

			executeCommands(leftHand1, leftHand2, leftHand3, rightHand1,
					rightHand2, rightHand3, basic, leftFoot1, leftFoot2,
					leftFoot3, rightFoot1, rightFoot2, rightFoot3, commands);
		}

		private void executeCommands(ImageView lh1, ImageView lh2,
				ImageView lh3, ImageView rh1, ImageView rh2, ImageView rh3,
				ImageView basic, ImageView lf1, ImageView lf2, ImageView lf3,
				ImageView rf1, ImageView rf2, ImageView rf3,
				List<String> commands) {
			Runnable runnable = new StringCommandExecutor(lh1, lh2, lh3, rh1,
					rh2, rh3, basic, lf1, lf2, lf3, rf1, rf2, rf3, commands);
			for (int i = 0; i < commands.size(); i++) { /* 解析&実行 */
				handler.post(runnable); /* 光らせる */

				try { /* 1秒待機 */
					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				handler.post(runnable); /* 光らせる */

				try { /* 1秒待機 */
					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}
	}

	/******************** ファイル保存 doSave(View view) *************************/
	/*
	 * @SuppressLint("WorldReadableFiles") public void doSave() { EditText
	 * editText1 = (EditText) this.findViewById(R.id.editText1); Editable str =
	 * editText1.getText(); FileOutputStream output = null; try { output =
	 * this.openFileOutput(path, Context.MODE_WORLD_READABLE);
	 * output.write(str.toString().getBytes()); } catch (FileNotFoundException
	 * e) { e.printStackTrace(); } catch (IOException e) { e.printStackTrace();
	 * } finally { try { output.close(); } catch (Exception e) {
	 * e.printStackTrace(); } } }
	 */

	/******************** ファイルロード doLoad(View view) *************************/
	/*
	 * public void doLoad() { EditText editText1 = (EditText)
	 * this.findViewById(R.id.editText1); FileInputStream input = null; try {
	 * input = this.openFileInput(path); byte[] buffer = new byte[1000];
	 * input.read(buffer); String s = new String(buffer).trim();
	 * editText1.setText(s); } catch (FileNotFoundException e) {
	 * e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }
	 * finally { try { input.close(); } catch (Exception e) {
	 * e.printStackTrace(); } } }
	 */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		super.onCreateOptionsMenu(menu);
		MenuItem item1 = menu.add("実行");
		MenuItem item2 = menu.add("プレイヤー");

		item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				Intent intent = new Intent(getApplication(),
						jp.eclipcebook.ActionActivity.class);
				changeScreen(intent);
				return false;
			}
		});
		item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				// doSave();
				Intent intent = new Intent(getApplication(),
						jp.eclipcebook.MainActivity.class);
				changeScreen(intent);
				return false;
			}
		});
		return true;
	}

	private void changeScreen(Intent intent) {
		TextView editText1 = (TextView) findViewById(R.id.editText1);
		TextView editText2 = (TextView) findViewById(R.id.editText2);
		intent.putExtra("lesson", editText1.getText().toString());
		intent.putExtra("message", editText2.getText().toString());
		intent.putExtra("text_data", text_data);

		this.startActivity(intent);
	}
}
