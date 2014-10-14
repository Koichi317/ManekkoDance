// Todoリスト
/* キーボードを出した時の画像潰れ解消法　http://d.hatena.ne.jp/Superdry/20110715/1310754502 */
/* フラグメントによるTabの実装 */
/* 絵文字の実装 */

package net.exkazuu.ManekkoDance.activities;

import java.util.ArrayList;
import java.util.List;

import jp.eclipcebook.R;
import net.exkazuu.ManekkoDance.DetectableSoftKeyLayout;
import net.exkazuu.ManekkoDance.IconContainer;
import net.exkazuu.ManekkoDance.ImageContainer;
import net.exkazuu.ManekkoDance.ImageInEdit;
import net.exkazuu.ManekkoDance.Lessons;
import net.exkazuu.ManekkoDance.command.StringCommandExecutor;
import net.exkazuu.ManekkoDance.command.StringCommandParser;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final int TEXT_VIEW = 1;
	private static final int IMAGE_VIEW = 0;
	private String lesson;
	private String message;
	private String text_data;

	private TextView textView;
	private DetectableSoftKeyLayout DSKLayout;
	private HorizontalScrollView iconList;

	private ImageContainer leftImages;
	private ImageContainer rightImages;

	private Thread thread;
	private CommandExecutor commandExecutor;

	@Override
	protected void onPause() {
		super.onPause();
		if (commandExecutor != null) {
			commandExecutor.died = true;
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setTitle("編集画面");
		setContentView(R.layout.main);

		iconList = (HorizontalScrollView) findViewById(R.id.iconList);

		leftImages = ImageContainer.createPiyoLeft(this);
		rightImages = ImageContainer.createPiyoRight(this);

		// 背景たち
		ImageView[][] cells = new ImageView[3][9];

		cells[0][0] = (ImageView) findViewById(R.id.image0_0);
		cells[1][0] = (ImageView) findViewById(R.id.image1_0);
		cells[2][0] = (ImageView) findViewById(R.id.image2_0);
		cells[0][1] = (ImageView) findViewById(R.id.image0_1);
		cells[1][1] = (ImageView) findViewById(R.id.image1_1);
		cells[2][1] = (ImageView) findViewById(R.id.image2_1);
		cells[0][2] = (ImageView) findViewById(R.id.image0_2);
		cells[1][2] = (ImageView) findViewById(R.id.image1_2);
		cells[2][2] = (ImageView) findViewById(R.id.image2_2);
		cells[0][3] = (ImageView) findViewById(R.id.image0_3);
		cells[1][3] = (ImageView) findViewById(R.id.image1_3);
		cells[2][3] = (ImageView) findViewById(R.id.image2_3);
		cells[0][4] = (ImageView) findViewById(R.id.image0_4);
		cells[1][4] = (ImageView) findViewById(R.id.image1_4);
		cells[2][4] = (ImageView) findViewById(R.id.image2_4);
		cells[0][5] = (ImageView) findViewById(R.id.image0_5);
		cells[1][5] = (ImageView) findViewById(R.id.image1_5);
		cells[2][5] = (ImageView) findViewById(R.id.image2_5);
		cells[0][6] = (ImageView) findViewById(R.id.image0_6);
		cells[1][6] = (ImageView) findViewById(R.id.image1_6);
		cells[2][6] = (ImageView) findViewById(R.id.image2_6);
		cells[0][7] = (ImageView) findViewById(R.id.image0_7);
		cells[1][7] = (ImageView) findViewById(R.id.image1_7);
		cells[2][7] = (ImageView) findViewById(R.id.image2_7);
		cells[0][8] = (ImageView) findViewById(R.id.image0_8);
		cells[1][8] = (ImageView) findViewById(R.id.image1_8);
		cells[2][8] = (ImageView) findViewById(R.id.image2_8);

		// 右側のテキストたち
		String[][] program = new String[3][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 3; j++) {
				program[j][i] = null;
			}
		}

		TextView text = (TextView) findViewById(R.id.tvCount1);
		text.setText(program[0][0] + program[1][0] + program[2][0] + "\n"
				+ program[0][1] + program[1][1] + program[2][1] + "\n"
				+ program[0][2] + program[1][2] + program[2][2] + "\n"
				+ program[0][3] + program[1][3] + program[2][3] + "\n"
				+ program[0][4] + program[1][4] + program[2][4] + "\n"
				+ program[0][5] + program[1][5] + program[2][5] + "\n"
				+ program[0][6] + program[1][6] + program[2][6] + "\n"
				+ program[0][7] + program[1][7] + program[2][7] + "\n"
				+ program[0][8] + program[1][8] + program[2][8]);

		// ドラッグ対象Viewとイベント処理クラスを紐付ける
		// アイコンたち
		ImageView dragView1 = (ImageView) findViewById(R.id.imageView1);
		ImageView dragView2 = (ImageView) findViewById(R.id.imageView2);
		ImageView dragView3 = (ImageView) findViewById(R.id.imageView3);
		ImageView dragView4 = (ImageView) findViewById(R.id.imageView4);
		ImageView dragView5 = (ImageView) findViewById(R.id.imageView5);
		ImageView dragView6 = (ImageView) findViewById(R.id.imageView6);
		ImageView dragView7 = (ImageView) findViewById(R.id.imageView7);
		ImageView dragView8 = (ImageView) findViewById(R.id.imageView8);
		ImageView dragView9 = (ImageView) findViewById(R.id.imageView9);
		ImageView dragView10 = (ImageView) findViewById(R.id.imageView10);
		ImageView dragView11 = (ImageView) findViewById(R.id.imageView11);
		ImageView dragGomi = (ImageView) findViewById(R.id.imageGomi);

		DragViewListener listener1 = new DragViewListener(dragView1, cells,
				program, text);
		dragView1.setOnTouchListener(listener1);
		DragViewListener listener2 = new DragViewListener(dragView2, cells,
				program, text);
		dragView2.setOnTouchListener(listener2);
		DragViewListener listener3 = new DragViewListener(dragView3, cells,
				program, text);
		dragView3.setOnTouchListener(listener3);
		DragViewListener listener4 = new DragViewListener(dragView4, cells,
				program, text);
		dragView4.setOnTouchListener(listener4);
		DragViewListener listener5 = new DragViewListener(dragView5, cells,
				program, text);
		dragView5.setOnTouchListener(listener5);
		DragViewListener listener6 = new DragViewListener(dragView6, cells,
				program, text);
		dragView6.setOnTouchListener(listener6);
		DragViewListener listener7 = new DragViewListener(dragView7, cells,
				program, text);
		dragView7.setOnTouchListener(listener7);
		DragViewListener listener8 = new DragViewListener(dragView8, cells,
				program, text);
		dragView8.setOnTouchListener(listener8);
		DragViewListener listener9 = new DragViewListener(dragView9, cells,
				program, text);
		dragView9.setOnTouchListener(listener9);
		DragViewListener listener10 = new DragViewListener(dragView10, cells,
				program, text);
		dragView10.setOnTouchListener(listener10);
		DragViewListener listener11 = new DragViewListener(dragView11, cells,
				program, text);
		dragView11.setOnTouchListener(listener11);
		DragViewListener listenerGomi = new DragViewListener(dragGomi, cells,
				program, text);
		dragGomi.setOnTouchListener(listenerGomi);
		/********** Lesson data　の 取得 **************/
		Intent intent = getIntent();
		lesson = intent.getStringExtra("lesson");
		message = intent.getStringExtra("message");
		text_data = intent.getStringExtra("text_data");
		Log.v("my_debug", "" + text_data);
		if (text_data != null) {

		}
		if (message.equals("3")) {

		}
		if (message.equals("4")) {

		}
		if (message.equals("5")) {

		}
		if (message.equals("6")) {

		}

		/******************* tabの実装と切り替え *****************/

		host = (TabHost) findViewById(R.id.tabhost);
		host.setup();

		TabSpec tab1 = host.newTabSpec("tab1");
		tab1.setIndicator("絵文字");
		tab1.setContent(R.id.tab1);
		host.addTab(tab1);

		/*
		 * TabSpec tab2 = host.newTabSpec("tab2"); tab2.setIndicator("絵文字");
		 * tab2.setContent(R.id.tab2); host.addTab(tab2);
		 */
		if (iconContainer == null) {
			iconContainer = new IconContainer(getApplication());
		}

		final MainActivity mainActivity = this;

		host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
			public void onTabChanged(String tabId) {
				Log.v("my_debug", "onTabChanged" + tabId);
				if (tabId == "tab2") {
					new Thread(new Runnable() {
						@Override
						public void run() {

							mainActivity.runOnUiThread(new Runnable() {
								@Override
								public void run() {

								}
							});
						}
					}).start();
				} else if (tabId == "tab1") { //

				}
			}
		});

		host.setCurrentTab(IMAGE_VIEW);

		/********** 音楽 **************/
		// View.OnClickListener piyoOnClickListener = new View.OnClickListener()
		// {
		//
		// public void onClick(View v) {
		// host.setCurrentTab(IMAGE_VIEW);
		// final Handler handler = new Handler();
		// if (thread == null || !thread.isAlive()) {
		// commandExecutor = new CommandExecutor(handler);
		// thread = new Thread(commandExecutor);
		// thread.start();
		// }
		// }
		// };
		// this.findViewById(R.id.frameLayoutPiyo).setOnClickListener(
		// piyoOnClickListener);
		// this.findViewById(R.id.frameLayoutPiyo2).setOnClickListener(
		// piyoOnClickListener);

		final Activity activity = this;
		final int limitation = Lessons.getLimitation(Integer.parseInt(message));
		final Button btnJudge = (Button) findViewById(R.id.btnJudge);

	}

	/******************** 構文解析＆実行 *************************/
	public final class CommandExecutor implements Runnable {
		private final Handler handler;
		private boolean died;

		private CommandExecutor(Handler handler) {
			this.handler = handler;
			this.died = false;
		}

		public void run() {

			textView = (TextView) findViewById(R.id.editText1);
			String commandsText = textView.getText().toString();
			// (imgTextView.getTextFromImage(iconContainer)); // 1行ずつ配列に収納

			List<String> leftCommands = new ArrayList<String>();
			List<Integer> leftNumbers = new ArrayList<Integer>();
			StringCommandParser.parse(commandsText, leftCommands, leftNumbers,
					true);

			List<String> rightCommands = new ArrayList<String>();
			List<Integer> rightNumbers = new ArrayList<Integer>();
			StringCommandParser.parse(commandsText, rightCommands,
					rightNumbers, false);

			executeCommands(leftImages, rightImages, leftCommands,
					rightCommands, leftNumbers, rightNumbers, textView);
		}

		private void executeCommands(ImageContainer leftImages,
				ImageContainer rightImages, List<String> leftCommands,
				List<String> rightCommands, List<Integer> leftNumbers,
				List<Integer> rightNumbers, TextView textView) {
			StringCommandExecutor leftRunnable = new StringCommandExecutor(
					leftImages, leftCommands, textView, leftNumbers,
					getApplicationContext(), true);
			StringCommandExecutor rightRunnable = new StringCommandExecutor(
					rightImages, rightCommands, textView, rightNumbers,
					getApplicationContext(), false);
			for (int i = 0; !died && i < leftCommands.size(); i++) { /* 解析&実行 */
				handler.post(leftRunnable); /* 光らせる */
				handler.post(rightRunnable); /* 光らせる */

				try { /* 1秒待機 */
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				handler.post(leftRunnable);
				handler.post(rightRunnable);

				try { /* 1秒待機 */
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				// for文から抜けて画像を初期化
				if (leftRunnable.existsError() || rightRunnable.existsError()) {
					handler.post(new Runnable() {
						public void run() {
							AlertDialog.Builder builder = new AlertDialog.Builder(
									MainActivity.this);
							builder.setTitle("間違った文を書いているよ");
							builder.setPositiveButton("もう一度Challenge",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											initializeImage();
										}
									});
							builder.show();
						}
					});
					return;
				}
			}

			handler.post(new Runnable() {
				public void run() {
					initializeImage();
				}
			});
		}

	}

	private TabHost host;
	private static IconContainer iconContainer;

	public void initializeImage() {
		leftImages = ImageContainer.createPiyoLeft(this);
		rightImages = ImageContainer.createPiyoRight(this);
	}

	/******************** メニュー作成 *************************/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		super.onCreateOptionsMenu(menu);
		MenuItem item1 = menu.add("ヘルプ");
		MenuItem item2 = menu.add("クリア");
		MenuItem item3 = menu.add("タイトル画面へ戻る");

		item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				changeHelpScreen();
				return false;
			}
		});
		item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				textView.getEditableText().clear();
				return false;
			}
		});
		item3.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				changeTitleScreen();
				return false;
			}
		});

		return true;
	}

	/************************* インテント（画面遷移） *****************************/
	public void changeActionScreen(View view) {
		host.setCurrentTab(TEXT_VIEW);
		Intent intent = new Intent(this,
				net.exkazuu.ManekkoDance.activities.ActionActivity.class);
		intent.putExtra("lesson", lesson);
		intent.putExtra("message", message);
		this.startActivity(intent);
	}

	public void changePartnerScreen(View view) { // お手本画面へ遷移
		host.setCurrentTab(TEXT_VIEW);
		Intent intent = new Intent(this,
				net.exkazuu.ManekkoDance.activities.PartnerActivity.class);
		intent.putExtra("lesson", lesson);
		intent.putExtra("message", message);
		intent.putExtra("text_data", textView.getText().toString());
		this.startActivity(intent);
	}

	public void changeHelpScreen() { // ヘルプ画面へ遷移
		host.setCurrentTab(TEXT_VIEW);
		Intent intent = new Intent(this, net.exkazuu.ManekkoDance.Help.class);
		intent.putExtra("lesson", lesson);
		intent.putExtra("message", message);
		this.startActivity(intent);
	}

	public void changeHelpScreen(View view) { // ヘルプ画面へ遷移
		host.setCurrentTab(TEXT_VIEW);
		Intent intent = new Intent(this, net.exkazuu.ManekkoDance.Help.class);
		intent.putExtra("lesson", lesson);
		intent.putExtra("message", message);
		this.startActivity(intent);
	}

	private void changeTitleScreen() {
		Intent intent = new Intent(this,
				net.exkazuu.ManekkoDance.activities.TitleActivity.class);
		this.startActivity(intent);
	}

	protected void onDestroy() {
		super.onDestroy();
		cleanupView(findViewById(R.id.root));
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
