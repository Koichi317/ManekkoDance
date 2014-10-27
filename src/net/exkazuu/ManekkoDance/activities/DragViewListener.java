package net.exkazuu.ManekkoDance.activities;

//import android.util.Log;

import jp.eclipcebook.R;

import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;

public class DragViewListener implements OnTouchListener {
    private ImageView dragView;
    private ImageView[][] cells;
    private String[][] program;
    private TextView text;
    private int[][] flag;
    //private int a;
    //private int b;
    private int oldx;
    private int oldy;

    public DragViewListener(ImageView dragView, ImageView[][] cells,
                            String[][] program, TextView text) {
        this.dragView = dragView;
        this.cells = cells;
        this.program = program;
        this.text = text;
        this.flag = flag;
        //this.a = a;
        //this.b = b;
    }

    public boolean onTouch(View view, MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        int left = dragView.getLeft() + (x - oldx);
        int top = dragView.getTop() + (y - oldy);

        int x_index = left / cells[0][0].getWidth();
        int y_index = top / cells[0][0].getHeight();

        int xx = x / cells[0][0].getWidth();
        int yy = y / cells[0][0].getHeight();

        //if (flag[x_index][y_index] == 1) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                //if (flag[a][b] == 1) {
                dragView.layout(left, top, left + dragView.getWidth(), top
                        + dragView.getHeight());
                //}
                break;
            case MotionEvent.ACTION_UP:
                //if (flag[a][b] != 1) {
                //return false;
                //}
                if (0 <= x_index && x_index <= 2 && 0 <= y_index && y_index <= 9) {
                    if (view.getId() == R.id.imageView1) {
                        cells[x_index][y_index]
                                .setImageResource(R.drawable.icon_right_hand_up);
                        program[x_index][y_index] = "右腕を上げる";
                    } else if (view.getId() == R.id.imageView2) {
                        cells[x_index][y_index]
                                .setImageResource(R.drawable.icon_right_hand_down);
                        program[x_index][y_index] = "右腕を下げる";
                    } else if (view.getId() == R.id.imageView3) {
                        cells[x_index][y_index]
                                .setImageResource(R.drawable.icon_left_hand_up);
                        program[x_index][y_index] = "左腕を上げる";
                    } else if (view.getId() == R.id.imageView4) {
                        cells[x_index][y_index]
                                .setImageResource(R.drawable.icon_left_hand_down);
                        program[x_index][y_index] = "左腕を下げる";
                    } else if (view.getId() == R.id.imageView5) {
                        cells[x_index][y_index]
                                .setImageResource(R.drawable.icon_loop);
                        program[x_index][y_index] = "くりかえし";
                    } else if (view.getId() == R.id.imageView6) {
                        cells[x_index][y_index]
                                .setImageResource(R.drawable.icon_kokomade);
                        program[x_index][y_index] = "ここまで";
                    } else if (view.getId() == R.id.imageView7) {
                        cells[x_index][y_index]
                                .setImageResource(R.drawable.icon_yellow);
                        program[x_index][y_index] = "黄色";
                    } else if (view.getId() == R.id.imageView8) {
                        cells[x_index][y_index]
                                .setImageResource(R.drawable.icon_orange);
                        program[x_index][y_index] = "茶色";
                    } else if (view.getId() == R.id.imageView9) {
                        cells[x_index][y_index]
                                .setImageResource(R.drawable.icon_if);
                        program[x_index][y_index] = "もしも";
                    } else if (view.getId() == R.id.imageView10) {
                        cells[x_index][y_index]
                                .setImageResource(R.drawable.icon_else);
                        program[x_index][y_index] = "もしくは";
                    } else if (view.getId() == R.id.imageView11) {
                        cells[x_index][y_index]
                                .setImageResource(R.drawable.icon_if_kokomade);
                        program[x_index][y_index] = "もしおわり";
                    } else if (view.getId() == R.id.imageView12) {
                        cells[x_index][y_index].setImageResource(R.drawable.haikei);
                        program[x_index][y_index] = "";
                    } else if (view.getId() == R.id.imageView01) {
                        cells[x_index][y_index].setImageResource(R.drawable.num1);
                        program[x_index][y_index] = "1";
                    } else if (view.getId() == R.id.imageView02) {
                        cells[x_index][y_index].setImageResource(R.drawable.num2);
                        program[x_index][y_index] = "2";
                    } else if (view.getId() == R.id.imageView03) {
                        cells[x_index][y_index].setImageResource(R.drawable.num3);
                        program[x_index][y_index] = "3";
                    } else if (view.getId() == R.id.imageView04) {
                        cells[x_index][y_index].setImageResource(R.drawable.num4);
                        program[x_index][y_index] = "4";
                    } else if (view.getId() == R.id.imageView05) {
                        cells[x_index][y_index].setImageResource(R.drawable.num5);
                        program[x_index][y_index] = "5";
                    } else if (view.getId() == R.id.imageView06) {
                        cells[x_index][y_index].setImageResource(R.drawable.num6);
                        program[x_index][y_index] = "6";
                    } else if (view.getId() == R.id.imageView07) {
                        cells[x_index][y_index].setImageResource(R.drawable.num7);
                        program[x_index][y_index] = "7";
                    } else if (view.getId() == R.id.imageView08) {
                        cells[x_index][y_index].setImageResource(R.drawable.num8);
                        program[x_index][y_index] = "8";
                    } else if (view.getId() == R.id.imageView09) {
                        cells[x_index][y_index].setImageResource(R.drawable.num9);
                        program[x_index][y_index] = "9";
                    } else if (view.getId() == R.id.imageView00) {
                        cells[x_index][y_index].setImageResource(R.drawable.num0);
                        program[x_index][y_index] = "0";

                    }
                    // Log.v("program" + x_index + y_index,
                    // program[x_index][y_index]);

                } else {
                    // 初期状態に戻しておきたい
                }
                text.setText(program[0][0] + program[1][0] + program[2][0] + "\n"
                        + program[0][1] + program[1][1] + program[2][1] + "\n"
                        + program[0][2] + program[1][2] + program[2][2] + "\n"
                        + program[0][3] + program[1][3] + program[2][3] + "\n"
                        + program[0][4] + program[1][4] + program[2][4] + "\n"
                        + program[0][5] + program[1][5] + program[2][5] + "\n"
                        + program[0][6] + program[1][6] + program[2][6] + "\n"
                        + program[0][7] + program[1][7] + program[2][7] + "\n"
                        + program[0][8] + program[1][8] + program[2][8] + "\n"
                        + program[0][9] + program[1][9] + program[2][9] + "\n"
                        + program[0][10] + program[1][10] + program[2][10] + "\n"
                        + program[0][11] + program[1][11] + program[2][11] + "\n");
        }

        oldx = x;
        oldy = y;
        //}
        return true;
    }
}