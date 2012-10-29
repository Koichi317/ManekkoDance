package jp.eclipcebook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

public class partnerActivity extends Activity {
	
	private String path = "mydata2.txt"; //file�ۑ�
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE); //�^�C�g���o�[��\��
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
	
	private final class CommandExecutor implements Runnable {
		private final Handler handler;

		private CommandExecutor(Handler handler) {
			this.handler = handler;
		}

		public void run() {
			TextView editText1 = (TextView) findViewById(R.id.editText1);
			ImageView image1 = (ImageView)findViewById(R.id.imageView1);

			String str = editText1.getText().toString();
			String[] commands = str.split("\n"); // 1�s���ɔz��Ɋi�[
			List<String> connectCommands = new ArrayList<String>();
			for(int i=0; i<commands.length; i++) {
				connectCommands.add(commands[i]);
			}
			List<String> expandedCommands = new ArrayList<String>();
			moveArray(connectCommands, expandedCommands);
			expandedCommands.add("\n ");

			Runnable runnable = new StringParser(image1, expandedCommands, 1);

			for (int i = 0; i < expandedCommands.size(); i++) { /* ���&���s */
				
				handler.post(runnable); /* ���点�� */
				
				try { /* 1�b�ҋ@ */
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		}

		private void moveArray(List<String> connectCommands, List<String> expandedCommands) {
			// TODO Auto-generated method stub
			Stack<Integer> loopStack = new Stack<Integer>();
			Stack<Integer> loopCountStack = new Stack<Integer>();
			for (int i = 0; i < connectCommands.size(); i++) {
				if(connectCommands.get(i) == null)
					continue;
				else if (connectCommands.get(i).contains("loop")) { //loop������
					loopStack.push(i);
					loopCountStack.push(readCount(connectCommands.get(i)));
				}
				else if(connectCommands.get(i).contains("�����܂�")) { //koko������
					int loopPosition = loopStack.pop();
					int loopCount = loopCountStack.pop();
					connectCommands.remove(i);
					connectCommands.remove(loopPosition);
					makeLoop(connectCommands, loopPosition, i-2, loopCount);
					i = loopPosition - 1;
				}
				else if(loopStack.empty()){ //�X�^�b�N����
					expandedCommands.add(connectCommands.get(i));
				}
			}
		}
		
		private void makeLoop(List<String> connectCommands, int firstIndex, int lastIndex, int count) {
			String[] str = new String[lastIndex - firstIndex + 1];
			for(int i = firstIndex; i <= lastIndex; i++) {
				str[i-firstIndex] = connectCommands.get(i);
			}
			for(int i = 0; i < count-1; i++) { //3��J��Ԃ��Ȃ�Ai < 2 (1�񕪁{2�񕪒ǉ�)
				for(int j = str.length-1; j >= 0; j--)  {
					connectCommands.add(firstIndex, str[j]);
				}
			}
		}
		
		private int readCount(String loopCount) {
			Pattern p = Pattern.compile("[0-9]");
			Matcher m = p.matcher(loopCount);
			if(!m.find()) {
				return 0;   //0��J��Ԃ�
			}else {
				int startIndex = m.start();
				int countNumber = Integer.parseInt(loopCount.substring(startIndex));
				return countNumber;
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
		EditText editText1 = (EditText)this.findViewById(R.id.editText1);
		Editable str = editText1.getText();
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

/******************** �t�@�C�����[�h doLoad(View view) *************************/	
	public void doLoad() { 
		EditText editText1 = (EditText)this.findViewById(R.id.editText1);
		FileInputStream input = null;
		try {
			input = this.openFileInput(path);
			byte[] buffer = new byte[1000];
			input.read(buffer);
			String s = new String(buffer).trim();
			editText1.setText(s);
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
		Intent intent = new Intent(getApplication(),jp.eclipcebook.MainActivity.class);
		this.startActivity(intent);
	}
}
