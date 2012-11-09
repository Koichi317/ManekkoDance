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
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
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
import android.widget.TabHost;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

	private String path = "mydata.txt"; // file�ۑ�

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("�v���C���[���");
		setContentView(R.layout.main);
		MediaPlayer bgm1 = MediaPlayer.create(this, R.raw.ikusei_gamen); // �Q�[�����y
		bgm1.start(); // BGM�X�^�[�g
		doLoad(); // �Z�[�u�f�[�^�����[�h

		/******* tab�̍쐬 **********/
		TabHost tabhost = this.getTabHost();
		TabHost.TabSpec spec1 = tabhost.newTabSpec("�r");
		spec1.setIndicator("�r");
		spec1.setContent(R.id.tab1);
		tabhost.addTab(spec1);
		TabHost.TabSpec spec2 = tabhost.newTabSpec("��");
		spec2.setIndicator("��");
		spec2.setContent(R.id.tab2);
		tabhost.addTab(spec2);
		TabHost.TabSpec spec3 = tabhost.newTabSpec("���");
		spec3.setIndicator("���");
		spec3.setContent(R.id.tab3);
		tabhost.addTab(spec3);
		TabHost.TabSpec spec4 = tabhost.newTabSpec("�\��");
		spec4.setIndicator("�\��");
		spec4.setContent(R.id.tab4);
		tabhost.addTab(spec4);
		TabHost.TabSpec spec5 = tabhost.newTabSpec("���̑�");
		spec5.setIndicator("���̑�");
		spec5.setContent(R.id.tab5);
		tabhost.addTab(spec5);

		Button btn5 = (Button) this.findViewById(R.id.button5);
		btn5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				final Handler handler = new Handler();
				Thread trd = new Thread(new CommandExecutor(handler));
				trd.start();
			}
		});
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) { // �A�v���𗧂��グ��������basic�̃A�j�����J�n
		super.onWindowFocusChanged(hasFocus);

		ImageView img = (ImageView) findViewById(R.id.imageView1);
		// AnimationDrawable��XML���\�[�X���w��
		img.setBackgroundResource(R.drawable.default_position);

		// AnimationDrawable���擾
		AnimationDrawable frameAnimation = (AnimationDrawable) img
				.getBackground();

		// �A�j���[�V�����̊J�n
		frameAnimation.start();
	}

	/******************** �\����́����s *************************/
	public final class CommandExecutor implements Runnable {
		private final Handler handler;

		private CommandExecutor(Handler handler) {
			this.handler = handler;
		}

		public void run() {
			TextView editText1 = (TextView) findViewById(R.id.editText1);
			ImageView image1 = (ImageView) findViewById(R.id.imageView1);
			String commandsText = editText1.getText().toString();

			List<String> commands = StringCommandParser.parse(commandsText);

			executeCommands(image1, commands);

			AnswerCheck answer = new AnswerCheck();
			answer.compare(commands); // �����̔z��ƃv���C���[�̔z����r
			Log.d("�f�o�b�O", "AnswerCheck:" + answer.show()); // �����A�s�����̕\��
		}

		private void executeCommands(ImageView image1,
				List<String> expandedCommands) {
			Runnable runnable = new StringCommandExecutor(image1,
					expandedCommands);
			for (int i = 0; i < expandedCommands.size(); i++) { /* ���&���s */
				handler.post(runnable); /* ���点�� */

				try { /* 1�b�ҋ@ */
					Thread.sleep(500);
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
		TextView editText1 = (TextView) findViewById(R.id.editText1);
		Editable str = editText1.getEditableText();
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, str);
		this.startActivity(intent);
	}

	private void changePartnerScreen() { // ����{��ʂ֑J��
		Intent intent = new Intent(this, jp.eclipcebook.PartnerActivity.class);
		this.startActivity(intent);
	}

}
