package jp.eclipcebook;

import android.graphics.drawable.AnimationDrawable;
import android.view.View;

public class Animation {
	
	public static void basicAnimation(View v) { //基本姿勢
		v.setBackgroundResource( R.drawable.default_position ); // リソースからアニメーションを読み込み、ビューに設定
        AnimationDrawable anim = (AnimationDrawable)v.getBackground();// ビューからアニメーションを取り出し
        anim.start(); //アニメーション開始
	}
	
	public static void rightHandUpAnimation( View v ){  //右腕を上げる
        v.setBackgroundResource( R.drawable.right_hand_up );
        AnimationDrawable anim = (AnimationDrawable)v.getBackground();
        anim.start();  
    }
	
	public static void rightHandDownAnimation( View v ){  //右腕を下げる
        v.setBackgroundResource( R.drawable.right_hand_down );
        AnimationDrawable anim = (AnimationDrawable)v.getBackground();
        anim.start();
    }
	
	public static void leftHandUpAnimation( View v ){  //左腕を上げる
        v.setBackgroundResource( R.drawable.left_hand_up );
        AnimationDrawable anim = (AnimationDrawable)v.getBackground();
        anim.start();
    }

	public static void leftHandDownAnimation( View v ){ //左腕を下げる
        v.setBackgroundResource( R.drawable.left_hand_down );
        AnimationDrawable anim = (AnimationDrawable)v.getBackground();
        anim.start();
    }
	
	public static void bothHandUpAnimation( View v ){ //両手を上げる
        v.setBackgroundResource( R.drawable.both_hand_up );
        AnimationDrawable anim = (AnimationDrawable)v.getBackground();
        anim.start();
    }

	public static void bothHandDownAnimation( View v ){ //両手を下げる
        v.setBackgroundResource( R.drawable.both_hand_down );
        AnimationDrawable anim = (AnimationDrawable)v.getBackground();
        anim.start();
    }

	public static void leftFootUpAnimation( View v ){ //左足を上げる
        v.setBackgroundResource( R.drawable.left_foot_up );
        AnimationDrawable anim = (AnimationDrawable)v.getBackground();
        anim.start();
    }

	public static void leftFootDownAnimation( View v ){ //左足を下げる
        v.setBackgroundResource( R.drawable.left_foot_down );
        AnimationDrawable anim = (AnimationDrawable)v.getBackground();
        anim.start();
    }

	public static void rightFootUpAnimation( View v ){ //右足を上げる
        v.setBackgroundResource( R.drawable.right_foot_up );
        AnimationDrawable anim = (AnimationDrawable)v.getBackground();
        anim.start();
    }

	public static void rightFootDownAnimation( View v ){ //右足を下げる
        v.setBackgroundResource( R.drawable.right_foot_down );
        AnimationDrawable anim = (AnimationDrawable)v.getBackground();
        anim.start();
    }

	public static void jumpAnimation( View v ){ //ジャンプする
        v.setBackgroundResource( R.drawable.jump );
        AnimationDrawable anim = (AnimationDrawable)v.getBackground();
        anim.start();
    }
}
