package net.exkazuu.ManekkoDance.activities;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.exkazuu.ManekkoDance.AnswerCheck;
import net.exkazuu.ManekkoDance.Help;
import net.exkazuu.ManekkoDance.ImageContainer;
import net.exkazuu.ManekkoDance.LessonData;
import net.exkazuu.ManekkoDance.LessonList;
import net.exkazuu.ManekkoDance.StringCommandExecutor;
import net.exkazuu.ManekkoDance.StringCommandParser;
import net.exkazuu.ManekkoDance.Timer;

import jp.eclipcebook.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

//import android.media.MediaPlayer;

public class ActionActivity extends Activity {

	private String path = "mydata2.txt";
	private String lesson;
	private String message;
	private String text_data;
	public boolean answerCheckEnd = false;

	// private MediaPlayer bgm;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("実行画面");
		setContentView(R.layout.action_screen);
		// doLoad();

		TextView playerEditText = (TextView) findViewById(R.id.editTextActionScreen1);
		TextView partnerEditText = (TextView) findViewById(R.id.editTextActionScreen2);
		Intent intent = getIntent();
		lesson = intent.getStringExtra("lesson");
		message = intent.getStringExtra("message");
		text_data = intent.getStringExtra("text_data");
		String playerCommandsText = text_data;
		String partnerCommandsText = lesson;

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

	public final class CommandExecutor implements Runnable {
		private final Handler handler;

		private CommandExecutor(Handler handler) {
			this.handler = handler;
		}

		public void run() {
			TextView playerEditText = (TextView) findViewById(R.id.editTextActionScreen1);
			TextView partnerEditText = (TextView) findViewById(R.id.editTextActionScreen2);

			String playerCommandsText = playerEditText.getText().toString();
			String partnerCommandsText = partnerEditText.getText().toString();

			List<String> leftPlayerCommands = new ArrayList<String>();
			List<Integer> leftPlayerNumbers = new ArrayList<Integer>();
			StringCommandParser.parse(playerCommandsText, leftPlayerCommands,
					leftPlayerNumbers, true);

			List<String> rightPlayerCommands = new ArrayList<String>();
			List<Integer> rightPlayerNumbers = new ArrayList<Integer>();
			StringCommandParser.parse(playerCommandsText, rightPlayerCommands,
					rightPlayerNumbers, false);

			List<Integer> leftPartnerNumbers = new ArrayList<Integer>();
			List<String> leftPartnerCommands = new ArrayList<String>();
			StringCommandParser.parse(partnerCommandsText, leftPartnerCommands,
					leftPartnerNumbers, true);

			List<Integer> rightPartnerNumbers = new ArrayList<Integer>();
			List<String> rightPartnerCommands = new ArrayList<String>();
			StringCommandParser.parse(partnerCommandsText,
					rightPartnerCommands, rightPartnerNumbers, false);

			Runnable leftPlayerAction = new StringCommandExecutor(
					new ImageContainer(
							(ImageView) findViewById(R.id.playerLeftHand1),
							(ImageView) findViewById(R.id.playerRightHand1),
							(ImageView) findViewById(R.id.playerBasic1),
							(ImageView) findViewById(R.id.playerLeftFoot1),
							(ImageView) findViewById(R.id.playerRightFoot1)),
					leftPlayerCommands, playerEditText, leftPlayerNumbers,
					getApplicationContext(), true);

			Runnable rightPlayerAction = new StringCommandExecutor(
					new ImageContainer(
							(ImageView) findViewById(R.id.playerLeftHand2),
							(ImageView) findViewById(R.id.playerRightHand2),
							(ImageView) findViewById(R.id.playerBasic2),
							(ImageView) findViewById(R.id.playerLeftFoot2),
							(ImageView) findViewById(R.id.playerRightFoot2)),
					rightPlayerCommands, playerEditText, rightPlayerNumbers,
					getApplicationContext(), true);

			Runnable leftPartnerAction = new StringCommandExecutor(
					new ImageContainer(
							(ImageView) findViewById(R.id.partnerLeftHand1),
							(ImageView) findViewById(R.id.partnerRightHand1),
							(ImageView) findViewById(R.id.partnerBasic1),
							(ImageView) findViewById(R.id.partnerLeftFoot1),
							(ImageView) findViewById(R.id.partnerRightFoot1)),
					leftPartnerCommands, true);

			Runnable rightPartnerAction = new StringCommandExecutor(
					new ImageContainer(
							(ImageView) findViewById(R.id.partnerLeftHand2),
							(ImageView) findViewById(R.id.partnerRightHand2),
							(ImageView) findViewById(R.id.partnerBasic2),
							(ImageView) findViewById(R.id.partnerLeftFoot2),
							(ImageView) findViewById(R.id.partnerRightFoot2)),
					rightPartnerCommands, false);

			final AnswerCheck answer = new AnswerCheck(leftPlayerCommands,
					leftPartnerCommands);
			answer.compare();
			final AnswerCheck answer2 = new AnswerCheck(rightPlayerCommands,
					rightPartnerCommands);
			answer2.compare();
			// answer.loopCheck(message, playerEditText);
			Log.v("tag", answer.show());

			// 解析&実行
			int maxSize = Math.max(leftPlayerCommands.size(),
					leftPartnerCommands.size());
			for (int i = 0; i < maxSize; i++) {

				if (i < leftPlayerCommands.size()) {
					handler.post(leftPlayerAction);
					handler.post(rightPlayerAction);
				}
				if (i < leftPartnerCommands.size()) {
					handler.post(leftPartnerAction);
					handler.post(rightPartnerAction);
				}

				try { /* 1秒待機 */
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (i < leftPlayerCommands.size()) {
					handler.post(leftPlayerAction);
					handler.post(rightPlayerAction);
				}
				if (i < leftPartnerCommands.size()) {
					handler.post(leftPartnerAction);
					handler.post(rightPartnerAction);
				}
				try { /* 1秒待機 */
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (StringCommandExecutor.errorCheck)
					break;
			}

			handler.post(new Runnable() {
				public void run() {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							ActionActivity.this);
					builder.setTitle(" ");
					if (answer.judge && answer2.judge) {

						if (Integer.parseInt(message) == 10) {
							// bgm = MediaPlayer.create(getApplicationContext(),
							// R.raw.perfect);
							// bgm.start();
							builder.setIcon(R.drawable.answer_ture);
							builder.setNegativeButton("タイトルへ戻る",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// bgm =
											// MediaPlayer.create(getApplicationContext(),
											// R.raw.select);
											// bgm.start();
											Intent intent = new Intent(
													getApplication(),
													net.exkazuu.ManekkoDance.activities.TitleActivity.class);
											startActivity(intent);
										}
									});

							builder.setPositiveButton("もう一度Challenge",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											// bgm =
											// MediaPlayer.create(getApplicationContext(),
											// R.raw.select);
											// bgm.start();
											changeMainScreen();
										}
									});
						} else {
							// bgm = MediaPlayer.create(getApplicationContext(),
							// R.raw.good_answer);
							// bgm.start();

							builder.setIcon(R.drawable.answer_ture);
							Timer.stopTimer();
							builder.setNegativeButton("次のLessonに進む",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// bgm =
											// MediaPlayer.create(getApplicationContext(),
											// R.raw.select);
											// bgm.start();
											int nextLessonNumber = Integer
													.parseInt(message) + 1;
											if (nextLessonNumber <= 3) {
												Intent intent = new Intent(
														getApplication(),
														net.exkazuu.ManekkoDance.activities.PartnerActivity.class);
												message = String
														.valueOf(nextLessonNumber);
												intent.putExtra("message",
														message);
												String str = LessonData
														.getLessonData(nextLessonNumber);
												lesson = str;
												intent.putExtra("lesson",
														lesson);
												startActivity(intent);
											} else {
												Intent intent = new Intent(
														getApplication(),
														net.exkazuu.ManekkoDance.LessonList.class);
												startActivity(intent);
											}
										}
									});

							builder.setNeutralButton(Timer.getResultTime(),
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}

							);

							builder.setPositiveButton("もう一度Challenge",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											// bgm =
											// MediaPlayer.create(getApplicationContext(),
											// R.raw.select);
											// bgm.start();
											changeMainScreen();
										}
									});
						}

					} else {
						// bgm = MediaPlayer.create(getApplicationContext(),
						// R.raw.fail);
						// bgm.start();
						builder.setIcon(R.drawable.answer_false);
						builder.setNegativeButton("Lessonを選択し直す",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										Intent intent = new Intent(
												getApplication(),
												net.exkazuu.ManekkoDance.LessonList.class);
										startActivity(intent);
									}
								});

						builder.setPositiveButton("もう一度Challenge",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										// bgm =
										// MediaPlayer.create(getApplicationContext(),
										// R.raw.select);
										// bgm.start();
										changeMainScreen();
									}
								});
					}

					builder.show();
				}
			});

		}

	}

	@SuppressLint("WorldReadableFiles")
	public void doSave() {
		EditText editText2 = (EditText) this
				.findViewById(R.id.editTextActionScreen2);
		Editable str = editText2.getText();
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

	// public void doLoad() {
	// EditText editText2 = (EditText)
	// this.findViewById(R.id.editTextActionScreen2);
	// FileInputStream input = null;
	// try {
	// input = this.openFileInput(path);
	// byte[] buffer = new byte[1000];
	// input.read(buffer);
	// String s = new String(buffer).trim();
	// editText2.setText(s);
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// } finally {
	// try {
	// input.close();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		super.onCreateOptionsMenu(menu);
		MenuItem item1 = menu.add("編集画面へ戻る");
		MenuItem item2 = menu.add("タイトルへ戻る");

		item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				changeMainScreen();
				return false;
			}
		});
		item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				doSave();
				changeTitleScreen();
				return false;
			}
		});
		return true;
	}

	public void changeMainScreen(View view) {
		Intent intent = new Intent(this,
				net.exkazuu.ManekkoDance.activities.MainActivity.class);
		TextView playerEditText = (TextView) findViewById(R.id.editTextActionScreen1);
		TextView partnerEditText = (TextView) findViewById(R.id.editTextActionScreen2);
		intent.putExtra("text_data", playerEditText.getText().toString());
		intent.putExtra("lesson", partnerEditText.getText().toString());
		intent.putExtra("message", message);
		this.startActivity(intent);
	}

	private void changeMainScreen() {
		Intent intent = new Intent(this,
				net.exkazuu.ManekkoDance.activities.MainActivity.class);
		TextView playerEditText = (TextView) findViewById(R.id.editTextActionScreen1);
		TextView partnerEditText = (TextView) findViewById(R.id.editTextActionScreen2);
		intent.putExtra("text_data", playerEditText.getText().toString());
		intent.putExtra("lesson", partnerEditText.getText().toString());
		intent.putExtra("message", message);
		this.startActivity(intent);
	}

	public void changePartnerScreen(View view) {
		Intent intent = new Intent(this,
				net.exkazuu.ManekkoDance.activities.PartnerActivity.class);
		TextView playerEditText = (TextView) findViewById(R.id.editTextActionScreen1);
		TextView partnerEditText = (TextView) findViewById(R.id.editTextActionScreen2);
		intent.putExtra("text_data", playerEditText.getText().toString());
		intent.putExtra("lesson", partnerEditText.getText().toString());
		intent.putExtra("message", message);
		this.startActivity(intent);
	}

	private void changeTitleScreen() {
		Intent intent = new Intent(this,
				net.exkazuu.ManekkoDance.activities.TitleActivity.class);
		this.startActivity(intent);
	}

	public void changeHelpScreen(View view) { // ヘルプ画面へ遷移
		// bgm = MediaPlayer.create(getApplicationContext(), R.raw.select);
		// bgm.start();
		Intent intent = new Intent(this, net.exkazuu.ManekkoDance.Help.class);
		intent.putExtra("lesson", lesson);
		intent.putExtra("message", message);
		intent.putExtra("text_data", text_data);
		this.startActivity(intent);
	}

	protected void onDestroy() {
		super.onDestroy();
		cleanupView(findViewById(R.id.actionRoot));
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
