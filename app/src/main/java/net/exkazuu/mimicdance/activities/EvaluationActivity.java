package net.exkazuu.mimicdance.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import net.exkazuu.mimicdance.CharacterImageViewSet;
import net.exkazuu.mimicdance.Lessons;
import net.exkazuu.mimicdance.R;
import net.exkazuu.mimicdance.Timer;
import net.exkazuu.mimicdance.interpreter.Interpreter;
import net.exkazuu.mimicdance.program.CodeParser;
import net.exkazuu.mimicdance.program.UnrolledProgram;

public class EvaluationActivity extends Activity {

    private String lesson;
    private String message;
    private String textData;

    private CharacterImageViewSet piyoImages;
    private CharacterImageViewSet altPiyoImages;
    private CharacterImageViewSet coccoLeftImages;
    private CharacterImageViewSet coccoRightImages;

    private Thread thread;
    private CommandExecutor commandExecutor;

    @Override
    protected void onPause() {
        super.onPause();
        if (commandExecutor != null) {
            commandExecutor.paused = true;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("実行画面");
        setContentView(R.layout.action_screen);

        TextView playerEditText = (TextView) findViewById(R.id.editTextActionScreen1);
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

        piyoImages = CharacterImageViewSet.createPiyoLeft(this);
        altPiyoImages = CharacterImageViewSet.createPiyoRight(this);
        coccoLeftImages = CharacterImageViewSet.createCoccoLeft(this);
        coccoRightImages = CharacterImageViewSet.createCoccoRight(this);
    }

    public final class CommandExecutor implements Runnable {
        private final Handler handler;
        private boolean paused;

        private CommandExecutor(Handler handler) {
            this.handler = handler;
            this.paused = false;
        }

        public void run() {
            TextView playerEditText = (TextView) findViewById(R.id.editTextActionScreen1);
            TextView partnerEditText = (TextView) findViewById(R.id.editTextActionScreen2);

            String playerCommandsText = playerEditText.getText().toString();
            String partnerCommandsText = partnerEditText.getText().toString();

            final UnrolledProgram piyoProgram = CodeParser.parse(playerCommandsText, true);
            final UnrolledProgram altPiyoProgram = CodeParser.parse(playerCommandsText, false);
            final UnrolledProgram coccoProgram = CodeParser.parse(partnerCommandsText, true);
            final UnrolledProgram altCoccoProgram = CodeParser.parse(partnerCommandsText, false);

            Interpreter piyoExecutor = new Interpreter(
                piyoImages, piyoProgram, playerEditText, getApplicationContext(), true);

            Interpreter altPiyoExecutor = new Interpreter(
                altPiyoImages, altPiyoProgram, playerEditText, getApplicationContext(), false);

            Interpreter coccoExecutor = new Interpreter(
                coccoLeftImages, coccoProgram, true);

            Interpreter altCoccoExecutor = new Interpreter(
                coccoRightImages, altCoccoProgram, false);

            Intent intent = getIntent();
            message = intent.getStringExtra("message");

            // 解析&実行(白と黄)
            if (message.equals("5") || message.equals("6")) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TextView which_birds = (TextView) findViewById(R.id.yellow_or_orange);
                        which_birds.setText("黄色いひよこの場合");
                    }
                });
            }
            int maxSize = Math.max(piyoProgram.size(), coccoProgram.size());
            for (int i = 0; !paused && i < maxSize; i++) {
                handler.post(piyoExecutor);
                handler.post(coccoExecutor);

                try { /* 1秒待機 */
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                handler.post(piyoExecutor);
                handler.post(coccoExecutor);
                try { /* 1秒待機 */
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (piyoExecutor.existsError()) {
                    break;
                }
            }

            if (message.equals("5") || message.equals("6")) {
                //表示するキャラクターを変更
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TextView which_birds = (TextView) findViewById(R.id.yellow_or_orange);
                        which_birds.setText("オレンジのひよこの場合");

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
                maxSize = Math.max(piyoProgram.size(), coccoProgram.size());
                for (int i = 0; !paused && i < maxSize; i++) {
                    handler.post(altPiyoExecutor);
                    handler.post(altCoccoExecutor);

                    try { /* 1秒待機 */
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    handler.post(altPiyoExecutor);
                    handler.post(altCoccoExecutor);
                    try { /* 1秒待機 */
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (altPiyoExecutor.existsError()) {
                        break;
                    }
                }
            }

            handler.post(new Runnable() {
                public void run() {
                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    final View layout = inflater.inflate(R.layout.dialog, (ViewGroup) findViewById(R.id.alertdialog_layout));

                    AlertDialog.Builder builder = new AlertDialog.Builder(EvaluationActivity.this);
                    builder.setTitle("あなたの答えは…？");
                    builder.setView(layout);
                    ImageView true_ans = (ImageView) layout.findViewById(R.id.ans_true);
                    ImageView false_ans = (ImageView) layout.findViewById(R.id.ans_false);

                    if (piyoProgram.semanticallyEquals(coccoProgram) && altPiyoProgram.semanticallyEquals(altCoccoProgram)) {
                        false_ans.setVisibility(View.GONE);
                        if (Integer.parseInt(message) == 10) {
                            builder.setNegativeButton("タイトルへ戻る",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                        DialogInterface dialog,
                                        int which) {
                                        Intent intent = new Intent(
                                            getApplication(), TitleActivity.class);
                                        startActivity(intent);
                                    }
                                });

                            builder.setPositiveButton("もう一度Challenge",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                        DialogInterface dialog,
                                        int which) {
                                        EvaluationActivity.this.finish();
                                    }
                                });
                        } else {
                            Timer.stopTimer();
                            builder.setNegativeButton("次のLessonに進む",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                        DialogInterface dialog,
                                        int which) {
                                        int nextLessonNumber = Integer.parseInt(message) + 1;
                                        if (nextLessonNumber <= 6) {
                                            Intent intent = new Intent(
                                                getApplication(), CoccoActivity.class);
                                            message = String.valueOf(nextLessonNumber);
                                            intent.putExtra("message", message);
                                            String str = Lessons.getAnswer(nextLessonNumber);
                                            lesson = str;
                                            intent.putExtra("lesson", lesson);
                                            startActivity(intent);
                                        } else {
                                            Intent intent = new Intent(
                                                getApplication(), LessonListActivity.class);
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
                                        EvaluationActivity.this.finish();
                                    }
                                });
                        }

                    } else {
                        true_ans.setVisibility(View.GONE);
                        builder.setNegativeButton("Lessonを選択し直す",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    Intent intent = new Intent(
                                        getApplication(), LessonListActivity.class);
                                    startActivity(intent);
                                }
                            });

                        builder.setPositiveButton("もう一度Challenge",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    EvaluationActivity.this.finish();
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
                EvaluationActivity.this.finish();
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
            CoccoActivity.class);
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
