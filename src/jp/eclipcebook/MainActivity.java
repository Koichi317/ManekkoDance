// Todo���X�g
/* �L�[�{�[�h���o�������̉摜�ׂ�����@�@http://d.hatena.ne.jp/Superdry/20110715/1310754502 */
/* �t���O�����g�ɂ��Tab�̎��� */
/* �G�����̎��� */

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
import android.widget.TextView;

public class MainActivity extends Activity {

	private String path = "mydata.txt"; // file�ۑ�
	private String lesson;
	private String message;
	private ImageInEdit mImageEdit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("�v���C���[���");
		setContentView(R.layout.main);
		
		mImageEdit = (ImageInEdit)findViewById(R.id.imageInEdit);

		/********** ���y **************/
		MediaPlayer bgm1 = MediaPlayer.create(this, R.raw.ikusei_gamen); // �Q�[�����y
		bgm1.start(); // BGM�X�^�[�g
		doLoad(); // �Z�[�u�f�[�^�����[�h

		/********** Lesson data�@�� �擾 **************/
		Intent intent = getIntent();
		lesson = intent.getStringExtra("lesson");
		message = intent.getStringExtra("message");

		Button btn5 = (Button) this.findViewById(R.id.button5);
		btn5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				final Handler handler = new Handler();
				Thread trd = new Thread(new CommandExecutor(handler));
				trd.start();
			}
		});
	}

	/*
	 * @Override public void onWindowFocusChanged(boolean hasFocus) { //
	 * �A�v���𗧂��グ��������basic�̃A�j�����J�n super.onWindowFocusChanged(hasFocus);
	 * 
	 * ImageView img = (ImageView) findViewById(R.id.imageView1); //
	 * AnimationDrawable��XML���\�[�X���w��
	 * img.setBackgroundResource(R.drawable.default_position);
	 * 
	 * // AnimationDrawable���擾 AnimationDrawable frameAnimation =
	 * (AnimationDrawable) img .getBackground();
	 * 
	 * // �A�j���[�V�����̊J�n frameAnimation.start(); }
	 */

	/******************** �\����́����s *************************/
	public final class CommandExecutor implements Runnable {
		private final Handler handler;

		private CommandExecutor(Handler handler) {
			this.handler = handler;
		}

		public void run() {
			TextView editText1 = (TextView) findViewById(R.id.editText1);
			ImageView leftHand1 = (ImageView) findViewById(R.id.playerLeftHand1);
			ImageView leftHand2 = (ImageView) findViewById(R.id.playerLeftHand2);
			ImageView leftHand3 = (ImageView) findViewById(R.id.playerLeftHand3);
			ImageView rightHand1 = (ImageView) findViewById(R.id.playerRightHand1);
			ImageView rightHand2 = (ImageView) findViewById(R.id.playerRightHand2);
			ImageView rightHand3 = (ImageView) findViewById(R.id.playerRightHand3);
			ImageView basic = (ImageView) findViewById(R.id.playerBasic);
			ImageView leftFoot1 = (ImageView) findViewById(R.id.playerLeftFoot1);
			ImageView leftFoot2 = (ImageView) findViewById(R.id.playerLeftFoot2);
			ImageView leftFoot3 = (ImageView) findViewById(R.id.playerLeftFoot3);
			ImageView rightFoot1 = (ImageView) findViewById(R.id.playerRightFoot1);
			ImageView rightFoot2 = (ImageView) findViewById(R.id.playerRightFoot2);
			ImageView rightFoot3 = (ImageView) findViewById(R.id.playerRightFoot3);

			String commandsText = editText1.getText().toString(); // 1�s���z��Ɏ��[

			List<String> commands = StringCommandParser.parse(commandsText);

			executeCommands(leftHand1, leftHand2, leftHand3, rightHand1,
					rightHand2, rightHand3, basic, leftFoot1, leftFoot2,
					leftFoot3, rightFoot1, rightFoot2, rightFoot3, commands);

		}

		private void executeCommands(ImageView lh1, ImageView lh2,
				ImageView lh3, ImageView rh1, ImageView rh2, ImageView rh3,
				ImageView basic, ImageView lf1, ImageView lf2, ImageView lf3,
				ImageView rf1, ImageView rf2, ImageView rf3,
				List<String> expandedCommands) {
			Runnable runnable = new StringCommandExecutor(lh1, lh2, lh3, rh1,
					rh2, rh3, basic, lf1, lf2, lf3, rf1, rf2, rf3,
					expandedCommands);
			for (int i = 0; i < expandedCommands.size(); i++) { /* ���&���s */
				handler.post(runnable); /* ���点�� */

				try { /* 1�b�ҋ@ */
					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				handler.post(runnable);

				try { /* 1�b�ҋ@ */
					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/******************** �{�^��(�G����)�̏��� *************************/
	public void doActionLeftHandUp(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("���r���グ��");

		mImageEdit .insertResourceImage(MainActivity.this, R.drawable.basic_piyo);
	}

	public void doActionLeftHandDown(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("���r��������");
	}

	public void doActionRightHandUp(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("�E�r���グ��");
	}

	public void doActionRightHandDown(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("�E�r��������");
	}

	public void doActionLeftFootUp(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("�������グ��");
	}

	public void doActionLeftFootDown(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("������������");
	}

	public void doActionRightFootUp(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("�E�����グ��");
	}

	public void doActionRightFootDown(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("�E����������");
	}

	public void doActionJump(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("�W�����v����");
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
		editText1.append("�����܂�");
	}

	/******************** �t�@�C���ۑ� doSave(View view) *************************/
	@SuppressLint("WorldReadableFiles")
	public void doSave() {
		EditText editText1 = (EditText) this.findViewById(R.id.editText1);
		Editable str = editText1.getText();
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

	/******************** �t�@�C�����[�h doLoad(View view) *************************/
	public void doLoad() {
		EditText editText1 = (EditText) this.findViewById(R.id.editText1);
		FileInputStream input = null;
		try {
			input = this.openFileInput(path);
			byte[] buffer = new byte[1000];
			input.read(buffer);
			String s = new String(buffer).trim();
			editText1.setText(s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

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
				doSave();
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
		this.startActivity(intent);
	}

	private void changePartnerScreen() { // ����{��ʂ֑J��
		Intent intent = new Intent(this, jp.eclipcebook.PartnerActivity.class);
		intent.putExtra("lesson", lesson);
		intent.putExtra("message", message);
		this.startActivity(intent);
	}

}
