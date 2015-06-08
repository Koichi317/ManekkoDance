package net.exkazuu.mimicdance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import net.exkazuu.mimicdance.Lessons;
import net.exkazuu.mimicdance.R;

public class CodingActivity extends BaseActivity {

    public static final int MAX_COLUMN = 3;
    public static final int MAX_ROW = 12;
    private int lessonNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coding);

        Intent intent = getIntent();
        lessonNumber = intent.getIntExtra("lessonNumber", 1);

        String[][] cellTexts = initializeCellTexts();
        ImageView[][] cellIcons = initializeCellIcons(cellTexts);
        initializeProgramIcons(cellTexts, cellIcons);
        initializeNumberIcons(cellTexts, cellIcons);

        ImageView[] steps = initializeStepNumbers();
        initializeMaximumStep(cellIcons, steps);
    }

    private String[][] initializeCellTexts() {
        String[][] cellTexts = new String[MAX_ROW][MAX_COLUMN];
        for (int row = 0; row < cellTexts.length; row++) {
            for (int column = 0; column < cellTexts[0].length; column++) {
                cellTexts[row][column] = "";
            }
        }
        return cellTexts;
    }

    private ImageView[][] initializeCellIcons(String[][] cellTexts) {
        ImageView[][] cellIcons = new ImageView[cellTexts.length][cellTexts[0].length];
        for (int row = 0; row < cellIcons.length; row++) {
            for (int column = 0; column < cellIcons[0].length; column++) {
                int id = getResources().getIdentifier("cell_" + row + "_" + column, "id", getPackageName());
                cellIcons[row][column] = (ImageView) findViewById(id);
                DragViewListener listener = new DragViewListener(this, cellIcons, cellTexts);
                cellIcons[row][column].setOnTouchListener(listener);
            }
        }
        return cellIcons;
    }

    private ImageView[] initializeStepNumbers() {
        ImageView[] stepIconView = new ImageView[MAX_ROW];
        for (int i = 0; i < stepIconView.length; i++) {
            int id = getResources().getIdentifier("step" + (i + 1), "id", getPackageName());
            stepIconView[i] = (ImageView) findViewById(id);
        }
        return stepIconView;
    }

    private void initializeProgramIcons(String[][] cellTexts, ImageView[][] cellIcons) {
        for (int i = 1; i < 12; i++) {
            if (!Lessons.hasLoop(lessonNumber) && 5 <= i && i <= 6) {
                continue;
            }
            if (!Lessons.hasIf(lessonNumber) && 7 <= i && i <= 11) {
                continue;
            }
            int id = getResources().getIdentifier("programIconView" + i, "id", getPackageName());
            ImageView imageView = (ImageView) findViewById(id);
            DragViewListener listener = new DragViewListener(this, cellIcons, cellTexts);
            imageView.setOnTouchListener(listener);
        }
    }

    private void initializeNumberIcons(String[][] cellTexts, ImageView[][] cellIcons) {
        if (Lessons.hasLoop(lessonNumber)) {
            for (int i = 0; i < 10; i++) {
                int id = getResources().getIdentifier("numberIconView" + i, "id", getPackageName());
                ImageView imageView = (ImageView) findViewById(id);
                DragViewListener listener = new DragViewListener(this, cellIcons, cellTexts);
                imageView.setOnTouchListener(listener);
            }
        }
    }

    private void initializeMaximumStep(ImageView[][] cells, ImageView[] step) {
        if (lessonNumber == 2) {
            setMaximumStep(cells, step, 12);
        } else if (lessonNumber == 4) {
            setMaximumStep(cells, step, 8);
        } else if (lessonNumber == 7) {
            setMaximumStep(cells, step, 12);
        } else {
            setMaximumStep(cells, step, 10);
        }
    }

    private void setMaximumStep(ImageView[][] cells, ImageView[] step, int maxStep) {
        for (int row = 0; row < maxStep; row++) {
            for (int column = 0; column < cells[0].length; column++) {
                cells[row][column].setVisibility(View.VISIBLE);
            }
            step[row].setVisibility(View.VISIBLE);
        }
        for (int row = maxStep; row < cells.length; row++) {
            for (int column = 0; column < cells[0].length; column++) {
                cells[row][column].setVisibility(View.INVISIBLE);
            }
            step[row].setVisibility(View.INVISIBLE);
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
        finish();
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
