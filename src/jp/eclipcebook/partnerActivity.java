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
import android.widget.Toast;

public class PartnerActivity extends Activity {

	private String path = "mydata2.txt"; // file�ۑ�

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE); //�^�C�g���o�[��\��
		setTitle("����{���");
		setContentView(R.layout.partner);
		doLoad();

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
		img.setBackgroundResource(R.drawable.default_position);
		AnimationDrawable frameAnimation = (AnimationDrawable) img
				.getBackground();
		frameAnimation.start();
	}

	private final class CommandExecutor implements Runnable {
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
		}

		private void executeCommands(ImageView image1, List<String> commands) {
			Runnable runnable = new StringCommandExecutor(image1, commands);
			for (int i = 0; i < commands.size(); i++) { /* ���&���s */
				handler.post(runnable); /* ���点�� */

				try { /* 1�b�ҋ@ */
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		super.onCreateOptionsMenu(menu);
		final Activity activity = this;
		MenuItem item1 = menu.add("���s");
		MenuItem item2 = menu.add("�N���A");
		MenuItem item3 = menu.add("�v���C���[");

		item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				Toast toast = Toast.makeText(activity, "This is Menu1",
						Toast.LENGTH_SHORT);
				toast.show();
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
				changeScreen();
				return false;
			}
		});
		return true;
	}

	private void changeScreen() {
		Intent intent = new Intent(getApplication(),
				jp.eclipcebook.MainActivity.class);
		this.startActivity(intent);
	}
}
