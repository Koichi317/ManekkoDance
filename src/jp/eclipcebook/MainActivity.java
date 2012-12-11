// Todo���X�g
/* �L�[�{�[�h���o�������̉摜�ׂ�����@�@http://d.hatena.ne.jp/Superdry/20110715/1310754502 */
/* �t���O�����g�ɂ��Tab�̎��� */
/* �G�����̎��� */

package jp.eclipcebook;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MainActivity extends Activity {

	// private String path = "mydata.txt"; // file�ۑ�
	private String lesson;
	private String message;
	private String text_data;

	private ImageInEdit mImageEdit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("�v���C���[���");
		setContentView(R.layout.main);

		mImageEdit = (ImageInEdit) findViewById(R.id.imageInEdit);

		/******************* tab�̎����Ɛ؂�ւ� *****************/

		TabHost host = (TabHost) findViewById(R.id.tabhost);
		host.setup();

		TabSpec tab1 = host.newTabSpec("tab1");
		tab1.setIndicator("����");
		tab1.setContent(R.id.tab1);
		host.addTab(tab1);

		TabSpec tab2 = host.newTabSpec("tab2");
		tab2.setIndicator("�G����");
		tab2.setContent(R.id.tab2);
		host.addTab(tab2);

		final InterconversionStringAndImage isa = new InterconversionStringAndImage();

		// host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
		// public void onTabChanged(String tabId) {
		// TextView editText1 = (TextView) findViewById(R.id.editText1);
		//
		// if (tabId == "tab2") {
		// mImageEdit.setText(editText1.getText().toString());
		// mImageEdit.replaceImage(MainActivity.this,
		// R.drawable.icon_left_hand_up);
		//
		// } else if (tabId == "tab1") {
		// String str = mImageEdit.getText().toString();
		// str = isa.convertImageToString(str);
		// editText1.setText(str);
		// Log.v("tag", str);
		// }
		// }
		// });

		/********** ���y **************/
		// MediaPlayer bgm1 = MediaPlayer.create(this, R.raw.ikusei_gamen); //
		// �Q�[�����y
		// bgm1.start(); // BGM�X�^�[�g

		// doLoad(); // �Z�[�u�f�[�^�����[�h

		/********** Lesson data�@�� �擾 **************/
		Intent intent = getIntent();
		lesson = intent.getStringExtra("lesson");
		message = intent.getStringExtra("message");
		text_data = intent.getStringExtra("text_data");
		TextView editText1 = (TextView) findViewById(R.id.editText1);
		editText1.setText(text_data);

		Button actionButton = (Button) this.findViewById(R.id.Button3);
		actionButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				final Handler handler = new Handler();
				Thread trd = new Thread(new CommandExecutor(handler));
				trd.start();
			}
		});
	}

	/******************** �\����́����s *************************/
	public final class CommandExecutor implements Runnable {
		private final Handler handler;

		private CommandExecutor(Handler handler) {
			this.handler = handler;
		}

		public void run() {
			TextView editText1 = (TextView) findViewById(R.id.editText1);
			ImageView leftHand1 = (ImageView) findViewById(R.id.playerLeftHand1);
			ImageView rightHand1 = (ImageView) findViewById(R.id.playerRightHand1);
			ImageView basic = (ImageView) findViewById(R.id.playerBasic);
			ImageView leftFoot1 = (ImageView) findViewById(R.id.playerLeftFoot1);
			ImageView rightFoot1 = (ImageView) findViewById(R.id.playerRightFoot1);

			String commandsText = editText1.getText().toString(); // 1�s���z��Ɏ��[
			List<Integer> numberSorting = new ArrayList<Integer>();
			List<String> commands = new ArrayList<String>();
			StringCommandParser.parse(commandsText, numberSorting, commands);
			executeCommands(
					new ImageContainer(leftHand1, rightHand1, basic, leftFoot1, rightFoot1),
					commands, editText1, numberSorting);

		}

		private void executeCommands(ImageContainer images, List<String> expandedCommands,
				TextView editText1, List<Integer> numberSorting) {
			Runnable runnable = new StringCommandExecutor(images, expandedCommands, editText1,
					numberSorting);
			for (int i = 0; i < expandedCommands.size(); i++) { /* ���&���s */
				handler.post(runnable); /* ���点�� */

				try { /* 1�b�ҋ@ */
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				handler.post(runnable);

				try { /* 1�b�ҋ@ */
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (StringCommandExecutor.errorCheck) // for�����甲���ĉ摜��������
					break;
			}

			if (StringCommandExecutor.errorCheck) {
				handler.post(new Runnable() {
					public void run() {
						AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
						builder.setTitle("�Ԉ�������������Ă����");
						builder.setPositiveButton("������xChallenge",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										initializeImage();
									}
								});
						builder.show();
					}
				});
			}else {
				handler.post(new Runnable() {
					public void run() {
						initializeImage();
					}
				});
			}
		}
		
	}

	/******************** �{�^��(�G����)�̏��� *************************/
	public void doActionLeftHandUp(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("���r���グ��");
		mImageEdit.insertResourceImage(MainActivity.this, R.drawable.icon_left_hand_up);
	}

	public void doActionLeftHandDown(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("���r��������");
		mImageEdit.insertResourceImage(MainActivity.this, R.drawable.icon_left_hand_down);
	}

	public void doActionRightHandUp(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("�E�r���グ��");
		mImageEdit.insertResourceImage(MainActivity.this, R.drawable.icon_right_hand_up);
	}

	public void doActionRightHandDown(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("�E�r��������");
		mImageEdit.insertResourceImage(MainActivity.this, R.drawable.icon_right_hand_down);

	}

	public void doActionLeftFootUp(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("�������グ��");
		mImageEdit.insertResourceImage(MainActivity.this, R.drawable.icon_left_foot_up);
	}

	public void doActionLeftFootDown(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("������������");
		mImageEdit.insertResourceImage(MainActivity.this, R.drawable.icon_left_foot_down);
	}

	public void doActionRightFootUp(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("�E�����グ��");
		mImageEdit.insertResourceImage(MainActivity.this, R.drawable.icon_right_foot_up);
	}

	public void doActionRightFootDown(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("�E����������");
		mImageEdit.insertResourceImage(MainActivity.this, R.drawable.icon_right_foot_down);
	}

	public void doActionJump(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("�W�����v����");
		mImageEdit.insertResourceImage(MainActivity.this, R.drawable.icon_jump);
	}

	public void doActionEnter(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("\n");
		mImageEdit.append("\n");
	}

	public void doActionLoop(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("loop");
		mImageEdit.insertResourceImage(MainActivity.this, R.drawable.icon_loop);
	}

	public void doActionKoko(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("�����܂�");
		mImageEdit.insertResourceImage(MainActivity.this, R.drawable.icon_kokomade);

	}

	public void doActionBackSpace(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("\r");
	}

	public void initializeImage() {
		ImageView leftHand1 = (ImageView) findViewById(R.id.playerLeftHand1);
		ImageView rightHand1 = (ImageView) findViewById(R.id.playerRightHand1);
		ImageView basic = (ImageView) findViewById(R.id.playerBasic);
		ImageView leftFoot1 = (ImageView) findViewById(R.id.playerLeftFoot1);
		ImageView rightFoot1 = (ImageView) findViewById(R.id.playerRightFoot1);
		leftHand1.setImageResource(R.drawable.piyo_left_hand_up1);
		rightHand1.setImageResource(R.drawable.piyo_right_hand_up1);
		basic.setImageResource(R.drawable.piyo_basic);
		leftFoot1.setImageResource(R.drawable.piyo_left_foot_up1);
		rightFoot1.setImageResource(R.drawable.piyo_right_foot_up1);
		leftHand1.setVisibility(View.VISIBLE);
		rightHand1.setVisibility(View.VISIBLE);
		leftFoot1.setVisibility(View.VISIBLE);
		rightFoot1.setVisibility(View.VISIBLE);
	}

	/******************** �t�@�C���ۑ� doSave(View view) *************************/
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

	/******************** �t�@�C�����[�h doLoad(View view) *************************/
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

	/******************** ���j���[�쐬 *************************/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		super.onCreateOptionsMenu(menu);
		MenuItem item1 = menu.add("���s");
		MenuItem item2 = menu.add("�N���A");
		MenuItem item3 = menu.add("����{");

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
				// doSave();
				changePartnerScreen();
				return false;
			}
		});
		return true;
	}

	/************************* �C���e���g�i��ʑJ�ځj *****************************/
	private void changeActionScreen() {
		Intent intent = new Intent(this, jp.eclipcebook.ActionActivity.class);
		TextView editText1 = (TextView) findViewById(R.id.editText1);
		intent.putExtra("text_data", editText1.getText().toString());
		intent.putExtra("lesson", lesson);
		intent.putExtra("message", message);
		this.startActivity(intent);
	}

	private void changePartnerScreen() { // ����{��ʂ֑J��
		Intent intent = new Intent(this, jp.eclipcebook.PartnerActivity.class);
		TextView editText1 = (TextView) findViewById(R.id.editText1);
		intent.putExtra("lesson", lesson);
		intent.putExtra("message", message);
		intent.putExtra("text_data", editText1.getText().toString());
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
			// ���ɂ�Drawable���g�p����Ώۂ�����΂����Œ��g��null��
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
