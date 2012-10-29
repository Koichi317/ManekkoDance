package jp.eclipcebook;

import java.util.List;

import android.widget.ImageView;

public class StringParser implements Runnable{
	
/**** �t�B�[���h ****/
	static int imageIndex1,imageIndex2;
	int i;
	ImageView image;
	List<String> expandedCommands;
	int imageNumber;
	
/**** �R���X�g���N�^ ****/	
	public StringParser(ImageView image,List<String> stringArray, int imageNumber) {
		this.image = image;
		this.expandedCommands = stringArray;
		this.imageNumber = imageNumber;
		imageIndex1 = 0;
		imageIndex2 = 0;
		i=0;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(imageNumber == 1) {
			i = imageIndex1;
		}
		else if(imageNumber == 2) {
			i = imageIndex2;
		}
		
		if(expandedCommands.get(i).indexOf("�E�����グ��") != -1) {
			Animation.rightFootUpAnimation(image);
		}
		else if(expandedCommands.get(i).indexOf("�E����������") != -1) {
			Animation.rightFootDownAnimation(image);
		}
		else if(expandedCommands.get(i).indexOf("�E�r���グ��") != -1) {
			if(expandedCommands.get(i).indexOf("���r���グ��") != -1) {
				Animation.bothHandUpAnimation(image);
			}else {
				Animation.rightHandUpAnimation(image);
			}
		}
		else if(expandedCommands.get(i).indexOf("�E�r��������") != -1) {
			if(expandedCommands.get(i).indexOf("���r��������") != -1) {
				Animation.bothHandDownAnimation(image);
			}else {
				Animation.rightHandDownAnimation(image);
			}
		}
		else if(expandedCommands.get(i).indexOf("�������グ��") != -1) {
			Animation.leftFootUpAnimation(image);
		}
		else if(expandedCommands.get(i).indexOf("������������") != -1) {
			Animation.leftFootDownAnimation(image);
		}
		else if(expandedCommands.get(i).indexOf("���r���グ��") != -1) {
			Animation.leftHandUpAnimation(image);
		}
		else if(expandedCommands.get(i).indexOf("���r��������") != -1) {
			Animation.leftHandDownAnimation(image);
		}
		else if(expandedCommands.get(i).indexOf("�W�����v����") != -1) {
			Animation.jumpAnimation(image);
		}else {
			Animation.basicAnimation(image);
		}
		if(imageNumber==1) {
			imageIndex1++;
		}
		else if(imageNumber==2) {
			imageIndex2++;
		}
	}	
}
