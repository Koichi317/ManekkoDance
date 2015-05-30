package net.exkazuu.mimicdance.activities;

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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import net.exkazuu.mimicdance.AnswerCheck;
import net.exkazuu.mimicdance.IconContainer;
import net.exkazuu.mimicdance.ImageContainer;
import net.exkazuu.mimicdance.ImageInEdit;
import net.exkazuu.mimicdance.Lessons;
import net.exkazuu.mimicdance.R;
import net.exkazuu.mimicdance.Timer;
import net.exkazuu.mimicdance.command.StringCommandExecutor;
import net.exkazuu.mimicdance.command.StringCommandParser;

import java.util.ArrayList;
import java.util.List;

public class ActionActivity extends Activity {

    private String lesson;
    private String message;
    private String textData;
    //private String textData;
    public boolean answerCheckEnd = false;

    private ImageContainer piyoLeftImages;
    private ImageContainer piyoRightImages;
    private ImageContainer coccoLeftImages;
    private ImageContainer coccoRightImages;

    private Thread thread;
    private CommandExecutor commandExecutor;

    @Override
    protected void onPause() {
        super.onPause();
        if (commandExecutor != null) {
            commandExecutor.died = true;
        }
    }

    ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("実行画面");
        setContentView(R.layout.action_screen);

        ImageInEdit playerEditText = (ImageInEdit) findViewById(R.id.editTextActionScreen1);
        TextView partnerEditText = (TextView) findViewById(R.id.editTextActionScreen2);
        Intent intent = getIntent();
        lesson = intent.getStringExtra("lesson");
        message = intent.getStringExtra("message");
        textData = intent.getStringExtra("text_data");
        String playerCommandsText = textData;
        String partnerCommandsText = lesson;

        FrameLayout alt_piyo = (FrameLayout) findViewById(R.id.alt_piyo);
        FrameLayout alt_cocco = (FrameLayout) findViewById(R.id.alt_cocco);
        alt_piyo.setVisibility(View.GONE);
        alt_cocco.setVisibility(View.GONE);

        playerEditText.setText(playerCommandsText);
        partnerEditText.setText(partnerCommandsText);

        Button btn1 = (Button) this.findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                final Handler handler = new Handler();
                if (thread == null || !thread.isAlive()) {
                    commandExecutor = new CommandExecutor(handler);
                    thread = new Thread(commandExecutor);
                    thread.start();
                }
            }
        });

        piyoLeftImages = ImageContainer.createPiyoLeft(this);
        piyoRightImages = ImageContainer.createPiyoRight(this);
        coccoLeftImages = ImageContainer.createCoccoLeft(this);
        coccoRightImages = ImageContainer.createCoccoRight(this);
/*
        if (message.equals("1") || message.equals("2") || message.equals("3")
				|| message.equals("4")) {
			alt_piyo.setVisibility(View.GONE);
			alt_cocco.setVisibility(View.GONE);
		}
*/

    }

    private static IconContainer IconContainer;

    public final class CommandExecutor implements Runnable {
        private final Handler handler;
        private boolean died;

        private CommandExecutor(Handler handler) {
            this.handler = handler;
            this.died = false;
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

            StringCommandExecutor leftPlayerExecutor = new StringCommandExecutor(
                piyoLeftImages, leftPlayerCommands, playerEditText,
                leftPlayerNumbers, getApplicationContext(), true);

            StringCommandExecutor rightPlayerExecutor = new StringCommandExecutor(
                piyoRightImages, rightPlayerCommands, playerEditText,
                rightPlayerNumbers, getApplicationContext(), false);

            StringCommandExecutor leftPartnerExecutor = new StringCommandExecutor(
                coccoLeftImages, leftPartnerCommands, true);

            StringCommandExecutor rightPartnerExecutor = new StringCommandExecutor(
                coccoRightImages, rightPartnerCommands, false);

            final AnswerCheck answer = new AnswerCheck(leftPlayerCommands,
                leftPartnerCommands);
            answer.compare();
            final AnswerCheck answer2 = new AnswerCheck(rightPlayerCommands,
                rightPartnerCommands);
            answer2.compare();
            Log.v("tag", answer.show());

            // 解析&実行(白と黄)
            int maxSize = Math.max(leftPlayerCommands.size(),
                leftPartnerCommands.size());
            for (int i = 0; !died && i < maxSize; i++) {
                if (i < leftPlayerCommands.size()) {
                    handler.post(leftPlayerExecutor);
                }
                if (i < leftPartnerCommands.size()) {
                    handler.post(leftPartnerExecutor);
                }

                try { /* 1秒待機 */
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (i < leftPlayerCommands.size()) {
                    handler.post(leftPlayerExecutor);
                }
                if (i < leftPartnerCommands.size()) {
                    handler.post(leftPartnerExecutor);
                }
                try { /* 1秒待機 */
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (leftPlayerExecutor.existsError()) {
                    break;
                }
            }

            //表示するキャラクターを変更
            handler.post(new Runnable() {
                @Override
                public void run() {
                    FrameLayout alt_piyo = (FrameLayout) findViewById(R.id.alt_piyo);
                    FrameLayout alt_cocco = (FrameLayout) findViewById(R.id.alt_cocco);
                    FrameLayout piyo = (FrameLayout) findViewById(R.id.piyo);
                    FrameLayout cocco = (FrameLayout) findViewById(R.id.cocco);

                    alt_piyo.setVisibility(View.VISIBLE);
                    alt_cocco.setVisibility(View.VISIBLE);
                    piyo.setVisibility(View.GONE);
                    cocco.setVisibility(View.GONE);

                }
            });

            try { /* 2秒待機 */
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //解析と実行(茶色と橙)
            for (int i = 0; !died && i < maxSize; i++) {
                if (i < leftPlayerCommands.size()) {
                    handler.post(rightPlayerExecutor);
                }
                if (i < leftPartnerCommands.size()) {
                    handler.post(rightPartnerExecutor);
                }

                try { /* 1秒待機 */
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (i < leftPlayerCommands.size()) {
                    handler.post(rightPlayerExecutor);
                }
                if (i < leftPartnerCommands.size()) {
                    handler.post(rightPartnerExecutor);
                }
                try { /* 1秒待機 */
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (rightPlayerExecutor.existsError()) {
                    break;
                }
            }

            handler.post(new Runnable() {
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                        ActionActivity.this);
                    builder.setTitle(" ");
                    if (answer.judge && answer2.judge) {

                        if (Integer.parseInt(message) == 10) {
                            builder.setIcon(R.drawable.answer_ture);
                            builder.setNegativeButton("タイトルへ戻る",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                        DialogInterface dialog,
                                        int which) {
                                        Intent intent = new Intent(
                                            getApplication(),
                                            net.exkazuu.mimicdance.activities.TitleActivity.class);
                                        startActivity(intent);
                                    }
                                });

                            builder.setPositiveButton("もう一度Challenge",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                        DialogInterface dialog,
                                        int which) {
                                        //(changeMainScreen();
                                        ActionActivity.this.finish();
                                    }
                                });
                        } else {
                            builder.setIcon(R.drawable.answer_ture);
                            Timer.stopTimer();
                            builder.setNegativeButton("次のLessonに進む",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                        DialogInterface dialog,
                                        int which) {
                                        int nextLessonNumber = Integer
                                            .parseInt(message) + 1;
                                        if (nextLessonNumber <= 6) {
                                            Intent intent = new Intent(
                                                getApplication(),
                                                net.exkazuu.mimicdance.activities.PartnerActivity.class);
                                            message = String
                                                .valueOf(nextLessonNumber);
                                            intent.putExtra("message",
                                                message);
                                            String str = Lessons
                                                .getAnswer(nextLessonNumber);
                                            lesson = str;
                                            intent.putExtra("lesson",
                                                lesson);
                                            startActivity(intent);
                                        } else {
                                            Intent intent = new Intent(
                                                getApplication(),
                                                LessonListActivity.class);
                                            startActivity(intent);
                                        }
                                        finish();
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
                                        //changeMainScreen();
                                        ActionActivity.this.finish();
                                    }
                                });
                        }

                    } else {
                        builder.setIcon(R.drawable.answer_false);
                        builder.setNegativeButton("Lessonを選択し直す",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    Intent intent = new Intent(
                                        getApplication(),
                                        LessonListActivity.class);
                                    startActivity(intent);
                                }
                            });

                        builder.setPositiveButton("もう一度Challenge",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    //changeMainScreen();
                                    ActionActivity.this.finish();
                                }
                            });
                    }

                    builder.show();
                }
            });

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu);
        MenuItem item1 = menu.add("画面へ戻る");
        MenuItem item2 = menu.add("タイトルへ戻る");

        item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                //changeMainScreen();
                ActionActivity.this.finish();
                return false;
            }
        });
        item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                changeTitleScreen();
                return false;
            }
        });
        return true;
    }

    public void changeMainScreen(View view) {
        finish();
    }


    public void changePartnerScreen(View view) {
        Intent intent = new Intent(this,
            net.exkazuu.mimicdance.activities.PartnerActivity.class);
        TextView playerEditText = (TextView) findViewById(R.id.editTextActionScreen1);
        TextView partnerEditText = (TextView) findViewById(R.id.editTextActionScreen2);
        intent.putExtra("text_data", playerEditText.getText().toString());
        intent.putExtra("lesson", partnerEditText.getText().toString());
        intent.putExtra("message", message);
        this.startActivity(intent);
    }

    private void changeTitleScreen() {
        Intent intent = new Intent(this,
            net.exkazuu.mimicdance.activities.TitleActivity.class);
        this.startActivity(intent);
    }

    public void changeHelpScreen(View view) { // ヘルプ画面へ遷移
        Intent intent = new Intent(this, HelpActivity.class);
        intent.putExtra("lesson", lesson);
        intent.putExtra("message", message);
        intent.putExtra("text_data", textData);
        this.startActivity(intent);
    }

}
