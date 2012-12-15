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
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MainActivity extends Activity {

	// private String path = "mydata.txt"; // file�ۑ�
	private String lesson;
	private String message;
	private String text_data;

	private ImageInEdit mImageEdit;
	private DetectableSoftKeyLayout DSKLayout;
	private FrameLayout fLayout;
	private LinearLayout buttonGroup2;
	private HorizontalScrollView iconList;
	private TextView editText1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("�v���C���[���");
		setContentView(R.layout.main);
		setTitleColor(0xffffff81);

		editText1 = (TextView) this.findViewById(R.id.editText1);
		mImageEdit = (ImageInEdit) findViewById(R.id.imageInEdit);
		mImageEdit.buildLayer();
		DSKLayout = (DetectableSoftKeyLayout) findViewById(R.id.root);
		DSKLayout.setListener(listner);
		fLayout = (FrameLayout) findViewById(R.id.frameLayout_piyo);
		buttonGroup2 = (LinearLayout) findViewById(R.id.buttonGroup2);
		iconList = (HorizontalScrollView) findViewById(R.id.iconList);

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

		host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
			public void onTabChanged(String tabId) {
				iconContainer icon = new iconContainer(getApplication());
				if (tabId == "tab2") {
					mImageEdit.setText(editText1.getText().toString());
					mImageEdit.replaceTextToImage(icon);

				} else if (tabId == "tab1") {
					String str = mImageEdit.getText().toString();
					str = isa.convertImageToString(str);
					editText1.setText(str);
					Log.v("����", str);
				}
			}
		});

		// editText1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		// @Override
		// public void onFocusChange(View v, boolean hasFocus) {
		// FrameLayout fLayout =
		// (FrameLayout)findViewById(R.id.frameLayout_piyo);
		// HorizontalScrollView buttonGroup =
		// (HorizontalScrollView)findViewById(R.id.buttonGroup);
		// InputMethodManager inputMethodManager = (InputMethodManager)
		// getSystemService(Context.INPUT_METHOD_SERVICE);
		// // �t�H�[�J�X���󂯎�����Ƃ�
		// if(hasFocus){
		// // �\�t�g�L�[�{�[�h��\������
		// inputMethodManager.showSoftInput(v, InputMethodManager.SHOW_FORCED);
		// fLayout.setVisibility(View.INVISIBLE);
		// buttonGroup.setVisibility(View.INVISIBLE);
		// }
		// // �t�H�[�J�X���O�ꂽ�Ƃ�
		// else{
		// // �\�t�g�L�[�{�[�h�����
		// inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
		// fLayout.setVisibility(View.VISIBLE);
		// buttonGroup.setVisibility(View.VISIBLE);
		// }
		// }
		// });
		// getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_AD);

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
		editText1 = (TextView) findViewById(R.id.editText1);
		editText1.setText(text_data);

		FrameLayout actionButton = (FrameLayout) this.findViewById(R.id.frameLayout_piyo);
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
			editText1 = (TextView)findViewById(R.id.editText1);
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
			} else {
				handler.post(new Runnable() {
					public void run() {
						initializeImage();
					}
				});
			}
		}

	}

	DetectableSoftKeyLayout.OnSoftKeyShownListener listner = new DetectableSoftKeyLayout.OnSoftKeyShownListener() {
		@Override
		public void onSoftKeyShown(boolean isShown) {
			if (isShown) {
				// �\�t�g�L�[�{�[�h���\������Ă���ꍇ
				// post�{�^�����\���ɂ���
				fLayout.setVisibility(View.GONE);
				buttonGroup2.setVisibility(View.GONE);
				iconList.setVisibility(View.VISIBLE);
			} else {
				// �\�t�g�L�[�{�[�h���\������ĂȂ���΁A�\������
				fLayout.setVisibility(View.VISIBLE);
				buttonGroup2.setVisibility(View.VISIBLE);
				iconList.setVisibility(View.GONE);
			}
		}
	};

	/******************** �{�^��(�G����)�̏��� *************************/
	public void doActionLeftHandUp(View view) {
		editText1 = (TextView) this.findViewById(R.id.editText1);
		int start = editText1.getSelectionStart();
		int end = editText1.getSelectionEnd();
		Editable editable = (Editable) editText1.getText();
		editable.replace(Math.min(start, end), Math.max(start, end), "���r���グ��");
		// mImageEdit.insertResourceImage(MainActivity.this,
		// R.drawable.icon_left_hand_up);
	}

	public void doActionLeftHandDown(View view) {
		editText1 = (TextView) this.findViewById(R.id.editText1);
		int start = editText1.getSelectionStart();
		int end = editText1.getSelectionEnd();
		Editable editable = (Editable) editText1.getText();
		editable.replace(Math.min(start, end), Math.max(start, end), "���r��������");
		// mImageEdit.insertResourceImage(MainActivity.this,
		// R.drawable.icon_left_hand_down);
	}

	public void doActionRightHandUp(View view) {
		editText1 = (TextView) this.findViewById(R.id.editText1);
		int start = editText1.getSelectionStart();
		int end = editText1.getSelectionEnd();
		Editable editable = (Editable) editText1.getText();
		editable.replace(Math.min(start, end), Math.max(start, end), "�E�r���グ��");
		// mImageEdit.insertResourceImage(MainActivity.this,
		// R.drawable.icon_right_hand_up);
	}

	public void doActionRightHandDown(View view) {
		editText1 = (TextView) this.findViewById(R.id.editText1);
		int start = editText1.getSelectionStart();
		int end = editText1.getSelectionEnd();
		Editable editable = (Editable) editText1.getText();
		editable.replace(Math.min(start, end), Math.max(start, end), "�E�r��������");
		// mImageEdit.insertResourceImage(MainActivity.this,
		// R.drawable.icon_right_hand_down);

	}

	public void doActionLeftFootUp(View view) {
		editText1 = (TextView) this.findViewById(R.id.editText1);
		int start = editText1.getSelectionStart();
		int end = editText1.getSelectionEnd();
		Editable editable = (Editable) editText1.getText();
		editable.replace(Math.min(start, end), Math.max(start, end), "�������グ��");
		// mImageEdit.insertResourceImage(MainActivity.this,
		// R.drawable.icon_left_foot_up);
	}

	public void doActionLeftFootDown(View view) {
		editText1 = (TextView) this.findViewById(R.id.editText1);
		int start = editText1.getSelectionStart();
		int end = editText1.getSelectionEnd();
		Editable editable = (Editable) editText1.getText();
		editable.replace(Math.min(start, end), Math.max(start, end), "������������");
		// mImageEdit.insertResourceImage(MainActivity.this,
		// R.drawable.icon_left_foot_down);
	}

	public void doActionRightFootUp(View view) {
		editText1 = (TextView) this.findViewById(R.id.editText1);
		int start = editText1.getSelectionStart();
		int end = editText1.getSelectionEnd();
		Editable editable = (Editable) editText1.getText();
		editable.replace(Math.min(start, end), Math.max(start, end), "�E�����グ��");
		// mImageEdit.insertResourceImage(MainActivity.this,
		// R.drawable.icon_right_foot_up);
	}

	public void doActionRightFootDown(View view) {
		editText1 = (TextView) this.findViewById(R.id.editText1);
		int start = editText1.getSelectionStart();
		int end = editText1.getSelectionEnd();
		Editable editable = (Editable) editText1.getText();
		editable.replace(Math.min(start, end), Math.max(start, end), "�E����������");
		// mImageEdit.insertResourceImage(MainActivity.this,
		// R.drawable.icon_right_foot_down);
	}

	public void doActionJump(View view) {
		editText1 = (TextView) this.findViewById(R.id.editText1);
		int start = editText1.getSelectionStart();
		int end = editText1.getSelectionEnd();
		Editable editable = (Editable) editText1.getText();
		editable.replace(Math.min(start, end), Math.max(start, end), "�W�����v����");
		// mImageEdit.insertResourceImage(MainActivity.this,
		// R.drawable.icon_jump);
	}

	public void doActionLoop(View view) {
		editText1 = (TextView) this.findViewById(R.id.editText1);
		int start = editText1.getSelectionStart();
		int end = editText1.getSelectionEnd();
		Editable editable = (Editable) editText1.getText();
		editable.replace( Math.min( start, end ), Math.max( start, end ), "loop");
		// mImageEdit.insertResourceImage(MainActivity.this,
		// R.drawable.icon_loop);
	}

	public void doActionKoko(View view) {
		editText1 = (TextView) this.findViewById(R.id.editText1);
		int start = editText1.getSelectionStart();
		int end = editText1.getSelectionEnd();
		Editable editable = (Editable) editText1.getText();
		editable.replace( Math.min( start, end ), Math.max( start, end ), "�����܂�");
		// mImageEdit.insertResourceImage(MainActivity.this,
		// R.drawable.icon_kokomade);

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
		MenuItem item1 = menu.add("�N���A");
		MenuItem item2 = menu.add("�^�C�g����ʂ֖߂�");
		// MenuItem item3 = menu.add("���߈ꗗ������");

		item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				editText1.getEditableText().clear();
				return false;
			}
		});
		item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				changeTitleScreen();
				return false;
			}
		});
		// item3.setOnMenuItemClickListener(new
		// MenuItem.OnMenuItemClickListener() {
		// public boolean onMenuItemClick(MenuItem item) {
		// return false;
		// }
		// });

		return true;
	}

	/************************* �C���e���g�i��ʑJ�ځj *****************************/
	public void changeActionScreen(View view) {
		Intent intent = new Intent(this, jp.eclipcebook.ActionActivity.class);
		intent.putExtra("text_data", editText1.getText().toString());
		intent.putExtra("lesson", lesson);
		intent.putExtra("message", message);
		this.startActivity(intent);
	}

	public void changePartnerScreen(View view) { // ����{��ʂ֑J��
		Intent intent = new Intent(this, jp.eclipcebook.PartnerActivity.class);
		intent.putExtra("lesson", lesson);
		intent.putExtra("message", message);
		intent.putExtra("text_data", editText1.getText().toString());
		this.startActivity(intent);
	}

	private void changeTitleScreen() {
		Intent intent = new Intent(this, jp.eclipcebook.TitleActivity.class);
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
