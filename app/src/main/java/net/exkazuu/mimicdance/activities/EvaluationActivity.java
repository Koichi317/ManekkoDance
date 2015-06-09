package net.exkazuu.mimicdance.activities;

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
import net.exkazuu.mimicdance.program.Block;
import net.exkazuu.mimicdance.program.CodeParser;
import net.exkazuu.mimicdance.program.UnrolledProgram;

public class EvaluationActivity extends BaseActivity {

    public static final int MaxStage = 10;
    private int lessonNumber;
    private String piyoCode;

    private UnrolledProgram piyoProgram;
    private UnrolledProgram altPiyoProgram;
    private UnrolledProgram coccoProgram;
    private UnrolledProgram altCoccoProgram;

    private CharacterImageViewSet piyoViewSet;
    private CharacterImageViewSet altPiyoViewSet;
    private CharacterImageViewSet coccoViewSet;
    private CharacterImageViewSet altCoccoViewSet;

    private Thread thread;
    private CommandExecutor commandExecutor;

    @Override
    protected void onPause() {
        super.onPause();
        // TODO: is this necessary?
        if (commandExecutor != null) {
            commandExecutor.paused = true;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadData();
        initializeScreen();
        initializeComponents();
    }

    /**
     * Intentから送られてきたプログラムをロードしてフィールドに格納する
     */
    private void loadData() {
        Intent intent = getIntent();
        lessonNumber = intent.getIntExtra("lessonNumber", 1);
        piyoCode = intent.getStringExtra("piyoCode");
        String coccoCode = Lessons.getCoccoCode(lessonNumber);

        Block piyoBlock = CodeParser.parse(piyoCode);
        Block coccoBlock = CodeParser.parse(coccoCode);
        piyoProgram = piyoBlock.unroll(true);
        altPiyoProgram = piyoBlock.unroll(false);
        coccoProgram = coccoBlock.unroll(true);
        altCoccoProgram = coccoBlock.unroll(false);
    }

    private void initializeScreen() {
        setContentView(R.layout.evaluation);
        FrameLayout altPiyoFrame = (FrameLayout) findViewById(R.id.alt_piyo);
        FrameLayout altCoccoFrame = (FrameLayout) findViewById(R.id.alt_cocco);
        altPiyoFrame.setVisibility(View.GONE);
        altCoccoFrame.setVisibility(View.GONE);
    }

    private void initializeComponents() {
        TextView playerEditText = (TextView) findViewById(R.id.txtStringCode);
        playerEditText.setText(piyoCode);

        piyoViewSet = CharacterImageViewSet.createPiyoLeft(this);
        altPiyoViewSet = CharacterImageViewSet.createPiyoRight(this);
        coccoViewSet = CharacterImageViewSet.createCoccoLeft(this);
        altCoccoViewSet = CharacterImageViewSet.createCoccoRight(this);

        Button button = (Button) this.findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Handler handler = new Handler();
                if (thread == null || !thread.isAlive()) {
                    commandExecutor = new CommandExecutor(handler);
                    thread = new Thread(commandExecutor);
                    thread.start();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu);

        menu.add("画面へ戻る").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                finish();
                return false;
            }
        });

        menu.add("タイトルへ戻る").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                startTitleActivity();
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_FOR_COCCO) {
            finish();
        }
    }

    public void startCoccoActivity(View view) {
        Intent intent = new Intent(this, CoccoActivity.class);
        intent.putExtra("lessonNumber", lessonNumber);
        intent.putExtra("piyoCode", piyoCode);
        startActivityForResult(intent, REQUEST_CODE_FOR_COCCO);
    }

    public void startHelpActivity(View view) {
        startHelpActivity();
    }

    public void startCodingActivity(View view) {
        finish();
    }

    public final class CommandExecutor implements Runnable {
        public static final int SLEEP_TIME = 500;
        private final Handler handler;
        private boolean paused;

        private CommandExecutor(Handler handler) {
            this.handler = handler;
            this.paused = false;
        }

        public void run() {
            TextView playerEditText = (TextView) findViewById(R.id.txtStringCode);

            Interpreter piyoExecutor = Interpreter.createForPiyo(
                piyoProgram, piyoViewSet, playerEditText, getApplicationContext());
            Interpreter altPiyoExecutor = Interpreter.createForPiyo(
                altPiyoProgram, altPiyoViewSet, playerEditText, getApplicationContext());
            Interpreter coccoExecutor = Interpreter.createForCocco(coccoProgram, coccoViewSet);
            Interpreter altCoccoExecutor = Interpreter.createForCocco(altCoccoProgram, altCoccoViewSet);

            // 解析&実行(白と黄)
            if (lessonNumber == 5 || lessonNumber == 6) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TextView whichBirds = (TextView) findViewById(R.id.yellow_or_orange);
                        whichBirds.setText("黄色いひよこの場合");
                    }
                });
            }
            int maxSize = Math.max(piyoProgram.size(), coccoProgram.size());
            dance(piyoExecutor, coccoExecutor, maxSize);

            if (lessonNumber == 5 || lessonNumber == 6) {
                //表示するキャラクターを変更
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TextView which_birds = (TextView) findViewById(R.id.yellow_or_orange);
                        which_birds.setText("オレンジのひよこの場合");

                        FrameLayout altPiyoFrame = (FrameLayout) findViewById(R.id.alt_piyo);
                        FrameLayout altCoccoFrame = (FrameLayout) findViewById(R.id.alt_cocco);
                        FrameLayout piyoFrame = (FrameLayout) findViewById(R.id.piyo);
                        FrameLayout coccoFrame = (FrameLayout) findViewById(R.id.cocco);

                        altPiyoFrame.setVisibility(View.VISIBLE);
                        altCoccoFrame.setVisibility(View.VISIBLE);
                        piyoFrame.setVisibility(View.GONE);
                        coccoFrame.setVisibility(View.GONE);
                    }
                });

                try {
                    Thread.sleep(SLEEP_TIME * 2);
                } catch (InterruptedException e) {
                }

                //解析と実行(茶色と橙)
                dance(altPiyoExecutor, altCoccoExecutor, maxSize);
            }

            handler.post(new Runnable() {
                public void run() {
                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    View layout = inflater.inflate(R.layout.dialog, (ViewGroup) findViewById(R.id.alertdialog_layout));

                    AlertDialog.Builder builder = new AlertDialog.Builder(EvaluationActivity.this);
                    builder.setTitle("あなたの答えは…？");
                    builder.setView(layout);
                    ImageView true_ans = (ImageView) layout.findViewById(R.id.ans_true);
                    ImageView false_ans = (ImageView) layout.findViewById(R.id.ans_false);

                    if (piyoProgram.semanticallyEquals(coccoProgram) && altPiyoProgram.semanticallyEquals(altCoccoProgram)) {
                        false_ans.setVisibility(View.GONE);
                        if (lessonNumber == MaxStage) {
                            builder.setNegativeButton("タイトルへ戻る",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                        DialogInterface dialog,
                                        int which) {
                                        startTitleActivity();
                                    }
                                });

                            builder.setPositiveButton("もう一度Challenge",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                        DialogInterface dialog,
                                        int which) {
                                        finish();
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
                                        startCoccoActivity(
                                            Math.min(lessonNumber + 1, Lessons.getLessonCount()), null);
                                        finish();
                                    }
                                });

                            builder.setNeutralButton(Timer.getResultTime(),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }
                            );

                            builder.setPositiveButton("もう一度Challenge",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                        DialogInterface dialog,
                                        int which) {
                                        finish();
                                    }
                                });
                        }

                    } else {
                        true_ans.setVisibility(View.GONE);
                        builder.setNegativeButton("Lessonを選択し直す",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startLessonListActivity();
                                }
                            });

                        builder.setPositiveButton("もう一度Challenge",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    finish();
                                }
                            });
                    }
                    builder.show();
                }
            });
        }

        private void dance(Interpreter piyoExecutor, Interpreter coccoExecutor, int maxSize) {
            for (int i = 0; !paused && i < maxSize; i++) {
                for (int j = 0; j < 2; j++) {
                    handler.post(piyoExecutor);
                    handler.post(coccoExecutor);

                    try { /* 1秒待機 */
                        Thread.sleep(SLEEP_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (piyoExecutor.existsError()) {
                    break;
                }
            }
        }
    }
}
