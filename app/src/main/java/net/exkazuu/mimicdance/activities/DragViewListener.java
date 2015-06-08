package net.exkazuu.mimicdance.activities;

//import android.util.Log;

import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

import net.exkazuu.mimicdance.R;

public class DragViewListener implements OnTouchListener {
    private final ImageView dragView;
    private final ImageView[][] cells;
    private final String[][] program;
    private final Intent intent;
    private int resb[][];
    private ImageView[][] canwrite;
    private int lessonNumber;
    private int oldX;
    private int oldY;

    public DragViewListener(ImageView dragView, ImageView[][] cells,
                            String[][] program, Intent intent, int[][] resb, ImageView[][] canwrite, int lessonNumber) {
        this.dragView = dragView;
        this.cells = cells;
        this.program = program;
        this.intent = intent;
        this.resb = resb;
        this.canwrite = canwrite;
        this.lessonNumber = lessonNumber;
    }

    public boolean onTouch(View view, MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        int left = dragView.getLeft() + (x - oldX);
        int top = dragView.getTop() + (y - oldY);

        int x_index = left / cells[0][0].getWidth();
        int y_index = top / cells[0][0].getHeight();

        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
                //修正案
                //コーディング領域にあるものを選択した場合、init_indexで命令のスワップ
                //アイコン一覧から選択した場合、init_left,topにで再表示
//                break;
            case MotionEvent.ACTION_MOVE:
                dragView.layout(left, top, left + dragView.getWidth(), top
                    + dragView.getHeight());
                break;
            case MotionEvent.ACTION_UP:
                if (x_index > 0) x_index--;
                if (0 <= x_index && x_index <= 2 && 0 <= y_index && y_index <= 11) {
                    //左のマス近辺の場合
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
                                    if (!program[i][j].equals("")) {
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

                //空白があったら詰める
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

                //レッスンによっては必要ない部分の命令を空白にする
                if (lessonNumber == 4) {
                    for (int i = 0; i < 3; i++) {
                        for (int j = 8; j < 12; j++) {
                            program[i][j] = "";
                        }
                    }
                } else if (lessonNumber == 2) {
                } else {
                    for (int i = 0; i < 3; i++) {
                        for (int j = 10; j < 12; j++) {
                            program[i][j] = "";
                        }
                    }
                }

                //アイコンに変更
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 12; j++) {
                        if (program[i][j].equals("右腕を上げる")) {
                            cells[i][j].setImageResource(R.drawable.icon_right_hand_up);
                        } else if (program[i][j].equals( "右腕を下げる" )) {
                            cells[i][j].setImageResource(R.drawable.icon_right_hand_down);
                        } else if (program[i][j].equals( "左腕を上げる")) {
                            cells[i][j].setImageResource(R.drawable.icon_left_hand_up);
                        } else if (program[i][j].equals( "左腕を下げる")) {
                            cells[i][j].setImageResource(R.drawable.icon_left_hand_down);
                        } else if (program[i][j].equals( "くりかえし")) {
                            cells[i][j].setImageResource(R.drawable.icon_loop);
                        } else if (program[i][j].equals( "ここまで") ){
                            cells[i][j].setImageResource(R.drawable.icon_kokomade);
                        } else if (program[i][j].equals( "黄色") ){
                            cells[i][j].setImageResource(R.drawable.icon_yellow);
                        } else if (program[i][j].equals( "茶色")) {
                            cells[i][j].setImageResource(R.drawable.icon_orange);
                        } else if (program[i][j].equals( "もしも")) {
                            cells[i][j].setImageResource(R.drawable.icon_if);
                        } else if (program[i][j].equals( "もしくは")) {
                            cells[i][j].setImageResource(R.drawable.icon_else);
                        } else if (program[i][j].equals( "もしおわり")) {
                            cells[i][j].setImageResource(R.drawable.icon_if_kokomade);
                        } else if (program[i][j].equals( "1")) {
                            cells[i][j].setImageResource(R.drawable.num1);
                        } else if (program[i][j].equals( "2")) {
                            cells[i][j].setImageResource(R.drawable.num2);
                        } else if (program[i][j].equals( "3")) {
                            cells[i][j].setImageResource(R.drawable.num3);
                        } else if (program[i][j].equals( "4")) {
                            cells[i][j].setImageResource(R.drawable.num4);
                        } else if (program[i][j].equals( "5")) {
                            cells[i][j].setImageResource(R.drawable.num5);
                        } else if (program[i][j].equals( "6")) {
                            cells[i][j].setImageResource(R.drawable.num6);
                        } else if (program[i][j].equals( "7")) {
                            cells[i][j].setImageResource(R.drawable.num7);
                        } else if (program[i][j].equals( "8")) {
                            cells[i][j].setImageResource(R.drawable.num8);
                        } else if (program[i][j].equals( "9")) {
                            cells[i][j].setImageResource(R.drawable.num9);
                        } else if (program[i][j].equals( "0")) {
                            cells[i][j].setImageResource(R.drawable.num0);
                        } else {
                            cells[i][j].setImageResource(R.drawable.haikei);
                        }
                    }
                }

                //次の入力場所の表示
                for (int j = 0; j < 12; j++) {
                    int flag = 0;
                    for (int i = 0; i < 3; i++) {
                        if (program[i][j].equals("")) {
                            if (flag == 0) {
                                canwrite[i][j].setImageResource(R.drawable.haikei2); //点線の四角
                                flag = 1;
                            } else {
                                canwrite[i][j].setImageResource(R.drawable.haikei); //空白
                            }
                        } else {
                            canwrite[i][j].setImageResource(R.drawable.haikei); //空白
                        }
                    }
                }

                StringBuilder builder = new StringBuilder();
                for (int column = 0; column < 3; column++) {
                    for (int row = 0; row < 12; row++) {
                        builder.append(program[column][row]);
                    }
                    builder.append("\n");
                }
                intent.putExtra("piyoCode", builder.toString());
                break;
        }

        oldX = x;
        oldY = y;
        return true;

    }
}
