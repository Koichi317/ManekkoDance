package net.exkazuu.mimicdance.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import net.exkazuu.mimicdance.CharacterImageViewSet;
import net.exkazuu.mimicdance.R;
import net.exkazuu.mimicdance.Timer;
import net.exkazuu.mimicdance.interpreter.Interpreter;
import net.exkazuu.mimicdance.program.CodeParser;
import net.exkazuu.mimicdance.program.UnrolledProgram;

public class CoccoActivity extends Activity {

    private String textData;
    private CharacterImageViewSet coccoViewSet;
    private CharacterImageViewSet altCoccoViewSet;
    private Thread thread;
    private String problemNumber;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("お手本画面");
        Timer.startTimer();
        setContentView(R.layout.partner);

        TextView editText1 = (TextView) findViewById(R.id.editText1);
        TextView editText2 = (TextView) findViewById(R.id.editText2);
        ImageView messageImageView1 = (ImageView) findViewById(R.id.imageView2);
        FrameLayout altCocco = (FrameLayout) findViewById(R.id.alt_cocco);

        Intent intent = getIntent();
        String data = intent.getStringExtra("lesson");
        problemNumber = intent.getStringExtra("message");
        textData = intent.getStringExtra("text_data");
        editText1.setText(data);
        editText2.setText(problemNumber);
        if (problemNumber.equals("1")) {
            messageImageView1.setImageResource(R.drawable.lesson_message1);
            altCocco.setVisibility(View.GONE);
        } else if (problemNumber.equals("2")) {
            messageImageView1.setImageResource(R.drawable.lesson_message2);
            altCocco.setVisibility(View.GONE);
        } else if (problemNumber.equals("3")) {
            messageImageView1.setImageResource(R.drawable.lesson_message3);
            altCocco.setVisibility(View.GONE);
        } else if (problemNumber.equals("4")) {
            messageImageView1.setImageResource(R.drawable.lesson_message4);
            altCocco.setVisibility(View.GONE);
        } else if (problemNumber.equals("5")) {
            messageImageView1.setImageResource(R.drawable.lesson_message5);
        } else if (problemNumber.equals("6")) {
            messageImageView1.setImageResource(R.drawable.lesson_message6);
        } else if (problemNumber.equals("7")) {
            messageImageView1.setImageResource(R.drawable.lesson_message7);
            altCocco.setVisibility(View.GONE);
        }/* else if (message.equals("8")) {
            messageImageView1.setImageResource(R.drawable.lesson_message8);
		} else if (message.equals("9")) {
			messageImageView1.setImageResource(R.drawable.lesson_message9);
		} else if (message.equals("10")) {
			messageImageView1.setImageResource(R.drawable.lesson_message10);
		} else {
			messageImageView1.setImageResource(R.drawable.lesson_message11);
		}*/

        Button btn5 = (Button) this.findViewById(R.id.btnShowCocco);

        final CoccoActivity activity = this;
        btn5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                coccoViewSet = CharacterImageViewSet.createCoccoLeft(activity);
                altCoccoViewSet = CharacterImageViewSet.createCoccoRight(activity);
                final Handler handler = new Handler();
                if (thread == null || !thread.isAlive()) {
                    thread = new Thread(new CommandExecutor(handler));
                    thread.start();
                }
            }
        });

        coccoViewSet = CharacterImageViewSet.createCoccoLeft(this);
        altCoccoViewSet = CharacterImageViewSet.createCoccoRight(this);
    }

    private final class CommandExecutor implements Runnable {
        private final Handler handler;

        private CommandExecutor(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            String commandsText = ((TextView) findViewById(R.id.editText1))
                .getText().toString();
            UnrolledProgram coccoProgram = CodeParser.parse(commandsText, true);
            UnrolledProgram altCoccoProgram = CodeParser.parse(commandsText, false);
            Runnable coccoExecutor = new Interpreter(coccoViewSet, coccoProgram, true);
            Runnable altCoccoExecutor = new Interpreter(altCoccoViewSet, altCoccoProgram, false);

            for (int i = 0; i < coccoProgram.size(); i++) { /* 解析&実行 */
                // 1コマ目を表示する（アニメーションは2コマから構成）
                handler.post(coccoExecutor); /* 光らせる */
                handler.post(altCoccoExecutor); /* 光らせる */

                Log.v("cocco", "first: " + i);

                try { /* 1秒待機 */
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 2コマ目を表示する（アニメーションは2コマから構成）
                handler.post(coccoExecutor);
                handler.post(altCoccoExecutor);

                Log.v("cocco", "second: " + i);

                try { /* 1秒待機 */
                    Thread.sleep(300);
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
        Intent intent = new Intent(getApplication(), MainActivity.class);
        TextView editText1 = (TextView) findViewById(R.id.editText1);
        TextView editText2 = (TextView) findViewById(R.id.editText2);
        intent.putExtra("lesson", editText1.getText().toString());
        intent.putExtra("message", editText2.getText().toString());
        intent.putExtra("text_data", textData);
        if (textData == null) {
            // 最初にコード入力画面に遷移するとき
            this.startActivity(intent);
        } else {
            // お手本確認に戻ってからコード入力画面に復帰する時
            CoccoActivity.this.finish();
        }
    }

    public void changeTitleScreen() {
        Intent intent = new Intent(getApplication(), TitleActivity.class);
        this.startActivity(intent);
    }
}
