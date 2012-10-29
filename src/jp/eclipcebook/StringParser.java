package jp.eclipcebook;

import java.util.List;

import android.widget.ImageView;

public class StringParser implements Runnable{
	
/**** フィールド ****/
	static int imageIndex1,imageIndex2;
	int i;
	ImageView image;
	List<String> expandedCommands;
	int imageNumber;
	
/**** コンストラクタ ****/	
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
		
		if(expandedCommands.get(i).indexOf("右足を上げる") != -1) {
			Animation.rightFootUpAnimation(image);
		}
		else if(expandedCommands.get(i).indexOf("右足を下げる") != -1) {
			Animation.rightFootDownAnimation(image);
		}
		else if(expandedCommands.get(i).indexOf("右腕を上げる") != -1) {
			if(expandedCommands.get(i).indexOf("左腕を上げる") != -1) {
				Animation.bothHandUpAnimation(image);
			}else {
				Animation.rightHandUpAnimation(image);
			}
		}
		else if(expandedCommands.get(i).indexOf("右腕を下げる") != -1) {
			if(expandedCommands.get(i).indexOf("左腕を下げる") != -1) {
				Animation.bothHandDownAnimation(image);
			}else {
				Animation.rightHandDownAnimation(image);
			}
		}
		else if(expandedCommands.get(i).indexOf("左足を上げる") != -1) {
			Animation.leftFootUpAnimation(image);
		}
		else if(expandedCommands.get(i).indexOf("左足を下げる") != -1) {
			Animation.leftFootDownAnimation(image);
		}
		else if(expandedCommands.get(i).indexOf("左腕を上げる") != -1) {
			Animation.leftHandUpAnimation(image);
		}
		else if(expandedCommands.get(i).indexOf("左腕を下げる") != -1) {
			Animation.leftHandDownAnimation(image);
		}
		else if(expandedCommands.get(i).indexOf("ジャンプする") != -1) {
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
