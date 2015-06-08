// Todoリスト
/* キーボードを出した時の画像潰れ解消法　http://d.hatena.ne.jp/Superdry/20110715/1310754502 */
/* フラグメントによるTabの実装 */
/* 絵文字の実装 */

package net.exkazuu.mimicdance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import net.exkazuu.mimicdance.R;

public class CodingActivity extends BaseActivity {

    private int lessonNumber;
    private String[][] program = new String[3][12];
    private ImageView[][] dragView = new ImageView[2][12];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coding);

        // 右側のテキストたち
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 3; j++) {
                program[j][i] = "";
            }
        }

        //記述可能部分
        ImageView[][] canwrite = new ImageView[3][12];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 12; j++) {
                int id = this.getResources().getIdentifier("canwrite" + i + "_" + j, "id", this.getPackageName());
                canwrite[i][j] = (ImageView) findViewById(id);
            }
        }
        for (int j = 0; j < 12; j++) {
            canwrite[0][j].setImageResource(R.drawable.haikei2);
        }

        // 背景たち
        Intent intent = getIntent();
        lessonNumber = intent.getIntExtra("lessonNumber", 1);
        int[][] resb = new int[3][12];
        ImageView[][] cells = new ImageView[3][12];
        DragViewListener[][] backgroundlistener = new DragViewListener[3][12];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 12; j++) {
                resb[i][j] = this.getResources().getIdentifier("image" + i + "_" + j, "id", this.getPackageName());
                cells[i][j] = (ImageView) findViewById(resb[i][j]);
                backgroundlistener[i][j] = new DragViewListener(cells[i][j], cells,
                    program, intent, resb, canwrite, lessonNumber);
                cells[i][j].setOnTouchListener(backgroundlistener[i][j]);
            }
        }

        // ドラッグ対象Viewとイベント処理クラスを紐付ける
        // アイコンたち
        //ImageView dragView1 = (ImageView) findViewById(R.id.imageView1);
        int[][] res = new int[2][12];
        DragViewListener[][] listener = new DragViewListener[2][12];
        //アイコンたち(右腕を上げる系)
        for (int i = 0; i < 11; i++) {
            res[0][i] = this.getResources().getIdentifier("imageView" + (i + 1), "id", this.getPackageName());
            dragView[0][i] = (ImageView) findViewById(res[0][i]);
            listener[0][i] = new DragViewListener(dragView[0][i], cells,
                program, intent, resb, canwrite, lessonNumber);
            dragView[0][i].setOnTouchListener(listener[0][i]);
        }
        //アイコンたち(数字達)
        for (int i = 0; i < 10; i++) {
            res[1][i] = this.getResources().getIdentifier("imageView" + 0 + i, "id", this.getPackageName());
            dragView[1][i] = (ImageView) findViewById(res[1][i]);
            listener[1][i] = new DragViewListener(dragView[1][i], cells,
                program, intent, resb, canwrite, lessonNumber);
            dragView[1][i].setOnTouchListener(listener[1][i]);
        }

        //行番号たち
        ImageView[] step = new ImageView[12];
        int[] resS = new int[12];
        for (int i = 0; i < 12; i++) {
            resS[i] = this.getResources().getIdentifier("step" + (i + 1), "id", this.getPackageName());
            step[i] = (ImageView) findViewById(resS[i]);
        }

        /********** Lesson data　の 取得 **************/
        editCodingSpace(canwrite, cells, step);
    }

    private void editCodingSpace(ImageView[][] canwrite, ImageView[][] cells, ImageView[] step) {
        if (lessonNumber == 2) {
            showExtraCommandSpace(canwrite, cells, step);
        }
        if (lessonNumber == 3) {
            showLoopCommand();
            showNumberCommands();
        }

        if (lessonNumber == 4) {
            showLoopCommand();
            showNumberCommands();
            hideCommandSpace(canwrite, cells, step);
        }
        if (lessonNumber == 5) {
            showIfCommands();

        }
        if (lessonNumber == 6) {
            showLoopCommand();
            showIfCommands();
            showNumberCommands();
        }
        if (lessonNumber == 7) {
            showExtraCommandSpace(canwrite, cells, step);
            showLoopCommand();
            showIfCommands();
            showNumberCommands();
        }
    }

    private void hideCommandSpace(ImageView[][] canwrite, ImageView[][] cells, ImageView[] step) {
        for (int j = 8; j < 10; j++) {
            for (int i = 0; i < 3; i++) {
                cells[i][j].setVisibility(View.INVISIBLE);
                canwrite[i][j].setVisibility(View.INVISIBLE);
            }
            step[j].setVisibility(View.INVISIBLE);
        }
    }

    private void showExtraCommandSpace(ImageView[][] canwrite, ImageView[][] cells, ImageView[] step) {
        for (int j = 10; j < 12; j++) {
            for (int i = 0; i < 3; i++) {
                cells[i][j].setVisibility(View.VISIBLE);
                canwrite[i][j].setVisibility(View.VISIBLE);
            }
            step[j].setVisibility(View.VISIBLE);
        }
    }

    private void showIfCommands() {
        for (int i = 6; i < 11; i++) {
            dragView[0][i].setVisibility(View.VISIBLE);
        }
    }

    private void showNumberCommands() {
        for (int i = 0; i < 10; i++) {
            dragView[1][i].setVisibility(View.VISIBLE);
        }
    }

    private void showLoopCommand() {
        for (int i = 4; i < 6; i++) {
            dragView[0][i].setVisibility(View.VISIBLE);
        }
    }

    /**
     * ***************** メニュー作成 ************************
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu);
        menu.add("ヘルプ").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                startHelpActivity();
                return false;
            }
        });
        menu.add("タイトル画面へ戻る").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                startTitleActivity();
                finish();
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cleanupView(findViewById(R.id.root));
        System.gc();
    }

    private static void cleanupView(View view) {
        if (view instanceof ImageButton) {
            ImageButton ib = (ImageButton) view;
            ib.setImageDrawable(null);
        } else if (view instanceof ImageView) {
            ImageView iv = (ImageView) view;
            iv.setImageDrawable(null);
            // } else if(view instanceof(XXX)) {
            // 他にもDrawableを使用する対象があればここで中身をnullに
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

    public void startCoccoActivity(View view) {
        startCoccoActivity(lessonNumber);
    }

    public void startEvaluationActivity(View view) {
        String piyoCode = getIntent().getStringExtra("piyoCode");
        if (piyoCode == null) {
            piyoCode = "";
        }
        startEvaluationActivity(lessonNumber, piyoCode);
    }

    public void startHelpActivity(View view) {
        startHelpActivity();
    }
}
