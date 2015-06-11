package net.exkazuu.mimicdance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

        String[][] cellTexts = initializeCellTexts(intent.getStringExtra("piyoCode"));
        ImageView[][] cellIcons = initializeCellIcons(cellTexts);
        initializeProgramIcons(cellTexts, cellIcons);
        initializeNumberIcons(cellTexts, cellIcons);

        ImageView[] steps = initializeStepNumbers();
        initializeMaximumStep(cellIcons, steps, Lessons.getMaxStep(lessonNumber));
    }

    private String[][] initializeCellTexts(String piyoCode) {
        String[][] cellTexts = new String[MAX_ROW][MAX_COLUMN];
        for (int row = 0; row < cellTexts.length; row++) {
            for (int column = 0; column < cellTexts[0].length; column++) {
                cellTexts[row][column] = "";
            }
        }

        String[] lines = piyoCode.split("\n");
        int maxRow = Math.min(lines.length, cellTexts.length);
        for (int row = 0; row < maxRow; row++) {
            String[] texts = lines[row].split(" ");
            int maxColumn = Math.min(texts.length, cellTexts[0].length);
            for (int column = 0; column < maxColumn; column++) {
                cellTexts[row][column] = texts[column];
            }
        }
        return cellTexts;
    }

    private ImageView[][] initializeCellIcons(String[][] cellTexts) {
        ImageView[][] cellIcons = new ImageView[cellTexts.length][cellTexts[0].length];
        DragViewListener lastListener = null;
        for (int row = 0; row < cellIcons.length; row++) {
            for (int column = 0; column < cellIcons[0].length; column++) {
                int id = getResources().getIdentifier("cell_" + row + "_" + column, "id", getPackageName());
                cellIcons[row][column] = (ImageView) findViewById(id);
                lastListener = new DragViewListener(this, cellIcons, cellTexts);
                cellIcons[row][column].setOnTouchListener(lastListener);
            }
        }
        lastListener.setProgramIcons();
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
            imageView.setVisibility(View.VISIBLE);
        }
    }

    private void initializeNumberIcons(String[][] cellTexts, ImageView[][] cellIcons) {
        if (Lessons.hasLoop(lessonNumber)) {
            for (int i = 0; i < 10; i++) {
                int id = getResources().getIdentifier("numberIconView" + i, "id", getPackageName());
                ImageView imageView = (ImageView) findViewById(id);
                DragViewListener listener = new DragViewListener(this, cellIcons, cellTexts);
                imageView.setOnTouchListener(listener);
                imageView.setVisibility(View.VISIBLE);
            }
        }
    }

    private void initializeMaximumStep(ImageView[][] cells, ImageView[] step, int maxStep) {
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
                startHelpActivity(false);
                return false;
            }
        });
        menu.add("タイトル画面へ戻る").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                startTitleActivity(true);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        startCoccoActivity(null);
    }

    public void startCoccoActivity(View view) {
        String piyoCode = getIntent().getStringExtra("piyoCode");
        startCoccoActivity(lessonNumber, piyoCode, true);
    }

    public void startEvaluationActivity(View view) {
        String piyoCode = getIntent().getStringExtra("piyoCode");
        startEvaluationActivity(lessonNumber, piyoCode, true);
    }

    public void startHelpActivity(View view) {
        startHelpActivity(false);
    }
}
