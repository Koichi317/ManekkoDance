package jp.eclipcebook;

import android.graphics.drawable.AnimationDrawable;
import android.view.View;

public class Animation {
	
	public static void basicAnimation(View v) { //��{�p��
		v.setBackgroundResource( R.drawable.default_position ); // ���\�[�X����A�j���[�V������ǂݍ��݁A�r���[�ɐݒ�
        AnimationDrawable anim = (AnimationDrawable)v.getBackground();// �r���[����A�j���[�V���������o��
        anim.start(); //�A�j���[�V�����J�n
	}
	
	public static void rightHandUpAnimation( View v ){  //�E�r���グ��
        v.setBackgroundResource( R.drawable.right_hand_up );
        AnimationDrawable anim = (AnimationDrawable)v.getBackground();
        anim.start();  
    }
	
	public static void rightHandDownAnimation( View v ){  //�E�r��������
        v.setBackgroundResource( R.drawable.right_hand_down );
        AnimationDrawable anim = (AnimationDrawable)v.getBackground();
        anim.start();
    }
	
	public static void leftHandUpAnimation( View v ){  //���r���グ��
        v.setBackgroundResource( R.drawable.left_hand_up );
        AnimationDrawable anim = (AnimationDrawable)v.getBackground();
        anim.start();
    }

	public static void leftHandDownAnimation( View v ){ //���r��������
        v.setBackgroundResource( R.drawable.left_hand_down );
        AnimationDrawable anim = (AnimationDrawable)v.getBackground();
        anim.start();
    }
	
	public static void bothHandUpAnimation( View v ){ //������グ��
        v.setBackgroundResource( R.drawable.both_hand_up );
        AnimationDrawable anim = (AnimationDrawable)v.getBackground();
        anim.start();
    }

	public static void bothHandDownAnimation( View v ){ //�����������
        v.setBackgroundResource( R.drawable.both_hand_down );
        AnimationDrawable anim = (AnimationDrawable)v.getBackground();
        anim.start();
    }

	public static void leftFootUpAnimation( View v ){ //�������グ��
        v.setBackgroundResource( R.drawable.left_foot_up );
        AnimationDrawable anim = (AnimationDrawable)v.getBackground();
        anim.start();
    }

	public static void leftFootDownAnimation( View v ){ //������������
        v.setBackgroundResource( R.drawable.left_foot_down );
        AnimationDrawable anim = (AnimationDrawable)v.getBackground();
        anim.start();
    }

	public static void rightFootUpAnimation( View v ){ //�E�����グ��
        v.setBackgroundResource( R.drawable.right_foot_up );
        AnimationDrawable anim = (AnimationDrawable)v.getBackground();
        anim.start();
    }

	public static void rightFootDownAnimation( View v ){ //�E����������
        v.setBackgroundResource( R.drawable.right_foot_down );
        AnimationDrawable anim = (AnimationDrawable)v.getBackground();
        anim.start();
    }

	public static void jumpAnimation( View v ){ //�W�����v����
        v.setBackgroundResource( R.drawable.jump );
        AnimationDrawable anim = (AnimationDrawable)v.getBackground();
        anim.start();
    }
}
