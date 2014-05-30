package net.exkazuu.ManekkoDance.activities;

import java.util.ArrayList;
import java.util.List;

import jp.eclipcebook.R;
import net.exkazuu.ManekkoDance.ImageContainer;
import net.exkazuu.ManekkoDance.StringCommandExecutor;
import net.exkazuu.ManekkoDance.StringCommandParser;
import net.exkazuu.ManekkoDance.Timer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class PartnerActivity extends Activity {

	private String textData;
	private ImageContainer leftImages;
	private ImageContainer rightImages;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("お手本画面");
		Timer.startTimer();
		setContentView(R.layout.partner);

		TextView editText1 = (TextView) findViewById(R.id.editText1);
		TextView editText2 = (TextView) findViewById(R.id.editText2);
		ImageView messageImageView1 = (ImageView) findViewById(R.id.imageView2);
		Intent intent = getIntent();
		String data = intent.getStringExtra("lesson");
		String message = intent.getStringExtra("message");
		textData = intent.getStringExtra("text_data");
		editText1.setText(data);
		editText2.setText(message);
		if (message.equals("1")) {
			messageImageView1.setImageResource(R.drawable.lesson_message1);
		} else if (message.equals("2")) {
			messageImageView1.setImageResource(R.drawable.lesson_message2);
		} else if (message.equals("3")) {
			messageImageView1.setImageResource(R.drawable.lesson_message3);
		} else if (message.equals("4")) {
			messageImageView1.setImageResource(R.drawable.lesson_message4);
		} else if (message.equals("5")) {
			messageImageView1.setImageResource(R.drawable.lesson_message5);
		} else if (message.equals("6")) {
			messageImageView1.setImageResource(R.drawable.lesson_message6);
		} else if (message.equals("7")) {
			messageImageView1.setImageResource(R.drawable.lesson_message7);
		} else if (message.equals("8")) {
			messageImageView1.setImageResource(R.drawable.lesson_message8);
		} else if (message.equals("9")) {
			messageImageView1.setImageResource(R.drawable.lesson_message9);
		} else if (message.equals("10")) {
			messageImageView1.setImageResource(R.drawable.lesson_message10);
		} else {
			messageImageView1.setImageResource(R.drawable.lesson_message11);
		}

		Button btn5 = (Button) this.findViewById(R.id.button5);
		btn5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				final Handler handler = new Handler();
				Thread trd = new Thread(new CommandExecutor(handler));
				trd.start();
			}
		});

		leftImages = ImageContainer.createCoccoLeft(this);
		rightImages = ImageContainer.createCoccoRight(this);
	}

	private final class CommandExecutor implements Runnable {
		private final Handler handler;

		private CommandExecutor(Handler handler) {
			this.handler = handler;
		}

		public void run() {
			String commandsText = ((TextView) findViewById(R.id.editText1))
					.getText().toString();
			List<String> leftCommands = new ArrayList<String>();
			List<Integer> leftNumbers = new ArrayList<Integer>();
			StringCommandParser.parse(commandsText, leftCommands, leftNumbers,
					true);

			List<String> rightCommands = new ArrayList<String>();
			List<Integer> rightNumbers = new ArrayList<Integer>();
			StringCommandParser.parse(commandsText, rightCommands,
					rightNumbers, false);

			executeCommands(leftImages, rightImages, leftCommands,
					rightCommands);
		}

		private void executeCommands(ImageContainer leftImages,
				ImageContainer rightImages, List<String> leftCommands,
				List<String> rightCommands) {
			Runnable leftRunnable = new StringCommandExecutor(leftImages,
					leftCommands, true);
			Runnable rightRunnable = new StringCommandExecutor(rightImages,
					rightCommands, false);
			for (int i = 0; i < leftCommands.size(); i++) { /* 解析&実行 */
				handler.post(leftRunnable); /* 光らせる */
				handler.post(rightRunnable); /* 光らせる */

				try { /* 1秒待機 */
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				handler.post(leftRunnable);
				handler.post(rightRunnable);

				try { /* 1秒待機 */
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		super.onCreateOptionsMenu(menu);
		MenuItem item1 = menu.add("編集画面");
		MenuItem item2 = menu.add("タイトル画面へ戻る");

		item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				changeMainScreen(null);
				return false;
			}
		});
		item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				// doSave();
				changeTitleScreen();
				return false;
			}
		});
		return true;
	}

	public void changeMainScreen(View view) {
		Intent intent = new Intent(getApplication(),
				net.exkazuu.ManekkoDance.activities.MainActivity.class);
		TextView editText1 = (TextView) findViewById(R.id.editText1);
		TextView editText2 = (TextView) findViewById(R.id.editText2);
		intent.putExtra("lesson", editText1.getText().toString());
		intent.putExtra("message", editText2.getText().toString());
		intent.putExtra("text_data", textData);
		this.startActivity(intent);
	}

	public void changeTitleScreen() {
		Intent intent = new Intent(getApplication(),
				net.exkazuu.ManekkoDance.activities.TitleActivity.class);
		this.startActivity(intent);
	}

	protected void onDestroy() {
		super.onDestroy();
		cleanupView(findViewById(R.id.partnerRoot));
		System.gc();
	}

	public static final void cleanupView(View view) {
		if (view instanceof ImageButton) {
			ImageButton ib = (ImageButton) view;
			ib.setImageDrawable(null);
		} else if (view instanceof ImageView) {
			ImageView iv = (ImageView) view;
			iv.setImageDrawable(null);
			// } else if(view instanceof(XXX)) {
			// 他にもDrawableを使用する対象があればここで中身をnullに
		}
		view.setBackgroundDrawable(null);
		if (view instanceof ViewGroup) {
			ViewGroup vg = (ViewGroup) view;
			int size = vg.getChildCount();
			for (int i = 0; i < size; i++) {
				cleanupView(vg.getChildAt(i));
			}
		}
	}
}
