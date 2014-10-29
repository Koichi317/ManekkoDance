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
    private int flag[][];
    private int resb[][];
    private ImageView[][] canwrite;
    private int oldx;
    private int oldy;

    public DragViewListener(ImageView dragView, ImageView[][] cells,
                            String[][] program, TextView text, int[][] flag, int[][] resb, ImageView[][] canwrite) {
        this.dragView = dragView;
        this.cells = cells;
        this.program = program;
        this.text = text;
        this.flag = flag;
        this.resb = resb;
        this.canwrite = canwrite;
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

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                dragView.layout(left, top, left + dragView.getWidth(), top
                        + dragView.getHeight());
                break;
            case MotionEvent.ACTION_UP:
                x_index--;
                if (0 <= x_index && x_index <= 2 && 0 <= y_index && y_index <= 11) {
                    if (view.getId() == R.id.imageView1) {
                        program[x_index][y_index] = "右腕を上げる";
                    } else if (view.getId() == R.id.imageView2) {
                        program[x_index][y_index] = "右腕を下げる";
                    } else if (view.getId() == R.id.imageView3) {
                        program[x_index][y_index] = "左腕を上げる";
                    } else if (view.getId() == R.id.imageView4) {
                        program[x_index][y_index] = "左腕を下げる";
                    } else if (view.getId() == R.id.imageView5) {
                        program[x_index][y_index] = "くりかえし";
                    } else if (view.getId() == R.id.imageView6) {
                        program[x_index][y_index] = "ここまで";
                    } else if (view.getId() == R.id.imageView7) {
                        program[x_index][y_index] = "黄色";
                    } else if (view.getId() == R.id.imageView8) {
                        program[x_index][y_index] = "茶色";
                    } else if (view.getId() == R.id.imageView9) {
                        program[x_index][y_index] = "もしも";
                    } else if (view.getId() == R.id.imageView10) {
                        program[x_index][y_index] = "もしくは";
                    } else if (view.getId() == R.id.imageView11) {
                        program[x_index][y_index] = "もしおわり";
                    } else if (view.getId() == R.id.imageView01) {
                        program[x_index][y_index] = "1";
                    } else if (view.getId() == R.id.imageView02) {
                        program[x_index][y_index] = "2";
                    } else if (view.getId() == R.id.imageView03) {
                        program[x_index][y_index] = "3";
                    } else if (view.getId() == R.id.imageView04) {
                        program[x_index][y_index] = "4";
                    } else if (view.getId() == R.id.imageView05) {
                        program[x_index][y_index] = "5";
                    } else if (view.getId() == R.id.imageView06) {
                        program[x_index][y_index] = "6";
                    } else if (view.getId() == R.id.imageView07) {
                        program[x_index][y_index] = "7";
                    } else if (view.getId() == R.id.imageView08) {
                        program[x_index][y_index] = "8";
                    } else if (view.getId() == R.id.imageView09) {
                        program[x_index][y_index] = "9";
                    } else if (view.getId() == R.id.imageView00) {
                        program[x_index][y_index] = "0";
                    } else if (view.getId() == R.id.imageView12) {
                        program[x_index][y_index] = "";
                    } else {
                        for (int i = 0; i < 3; i++) {
                            for (int j = 0; j < 12; j++) {
                                //resb[i][j] = this.getResources().getIdentifier("image" + i + "_" + j, "id", this.getPackageName());
                                if (view.getId() == resb[i][j]) {
                                    if (program[i][j] != "") {
                                        if (x_index == i && y_index == j) {
                                        } else {
                                            program[x_index][y_index] = program[i][j];
                                            program[i][j] = "";
                                            break;
                                        }
                                    }
                                }

                            }
                        }
                    }
                } else if (3 <= x_index && x_index <= 4 && 10 <= y_index && y_index <= 11) {
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 12; j++) {
                            if (view.getId() == resb[i][j]) {
                                program[i][j] = "";
                            }
                        }
                    }
                }

                for (int j = 0; j < 12; j++) {
                    for (int count = 0; count < 2; count++) {
                        for (int i = 0; i < 2; i++) {
                            if (program[i][j] == "") {
                                program[i][j] = program[i + 1][j];
                                program[i + 1][j] = "";
                            }
                        }
                    }
                }
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 12; j++) {
                        if (program[i][j] == "右腕を上げる") {
                            cells[i][j].setImageResource(R.drawable.icon_right_hand_up);
                        } else if (program[i][j] == "右腕を下げる") {
                            cells[i][j].setImageResource(R.drawable.icon_right_hand_down);
                        } else if (program[i][j] == "左腕を上げる") {
                            cells[i][j].setImageResource(R.drawable.icon_left_hand_up);
                        } else if (program[i][j] == "左腕を下げる") {
                            cells[i][j].setImageResource(R.drawable.icon_left_hand_down);
                        } else if (program[i][j] == "くりかえし") {
                            cells[i][j].setImageResource(R.drawable.icon_loop);
                        } else if (program[i][j] == "ここまで") {
                            cells[i][j].setImageResource(R.drawable.icon_kokomade);
                        } else if (program[i][j] == "黄色") {
                            cells[i][j].setImageResource(R.drawable.icon_yellow);
                        } else if (program[i][j] == "茶色") {
                            cells[i][j].setImageResource(R.drawable.icon_orange);
                        } else if (program[i][j] == "もしも") {
                            cells[i][j].setImageResource(R.drawable.icon_if);
                        } else if (program[i][j] == "もしくは") {
                            cells[i][j].setImageResource(R.drawable.icon_else);
                        } else if (program[i][j] == "もしおわり") {
                            cells[i][j].setImageResource(R.drawable.icon_if_kokomade);
                        } else if (program[i][j] == "1") {
                            cells[i][j].setImageResource(R.drawable.num1);
                        } else if (program[i][j] == "2") {
                            cells[i][j].setImageResource(R.drawable.num2);
                        } else if (program[i][j] == "3") {
                            cells[i][j].setImageResource(R.drawable.num3);
                        } else if (program[i][j] == "4") {
                            cells[i][j].setImageResource(R.drawable.num4);
                        } else if (program[i][j] == "5") {
                            cells[i][j].setImageResource(R.drawable.num5);
                        } else if (program[i][j] == "6") {
                            cells[i][j].setImageResource(R.drawable.num6);
                        } else if (program[i][j] == "7") {
                            cells[i][j].setImageResource(R.drawable.num7);
                        } else if (program[i][j] == "8") {
                            cells[i][j].setImageResource(R.drawable.num8);
                        } else if (program[i][j] == "9") {
                            cells[i][j].setImageResource(R.drawable.num9);
                        } else if (program[i][j] == "0") {
                            cells[i][j].setImageResource(R.drawable.num0);
                        } else {
                            cells[i][j].setImageResource(R.drawable.haikei);
                        }
                    }
                }
                for (int j = 0; j < 12; j++) {
                    int flag = 0;
                    for (int i = 0; i < 3; i++) {
                        if (program[i][j] == "") {
                            if (flag == 0) {
                                canwrite[i][j].setImageResource(R.drawable.haikei2);
                                flag = 1;
                            }else{
                                canwrite[i][j].setImageResource(R.drawable.haikei);
                            }
                        } else {
                            canwrite[i][j].setImageResource(R.drawable.haikei);
                        }
                    }
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
        return true;

    }
}
