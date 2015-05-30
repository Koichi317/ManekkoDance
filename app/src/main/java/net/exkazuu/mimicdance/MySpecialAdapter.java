package net.exkazuu.mimicdance;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MySpecialAdapter extends ArrayAdapter<String> {

    public MySpecialAdapter(Context context, int textViewResourceId,
                            List<String> objects) {
        super(context, textViewResourceId, objects);
        // TODO Auto-generated constructor stub
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        if (position % 2 == 0) {
            view.setBackgroundColor(Color.rgb(255, 153, 255));
        } else {
            view.setBackgroundColor(Color.rgb(255, 204, 204));
        }
        TextView v = (TextView) view;
        v.setTextSize(18);
        return view;
    }
}
