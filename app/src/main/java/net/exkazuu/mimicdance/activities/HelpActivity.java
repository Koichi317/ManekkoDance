package net.exkazuu.mimicdance.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import net.exkazuu.mimicdance.R;

public class HelpActivity extends Activity {
    public static final int MinPageNumber = 1;
    public static final int MaxPageNumber = 7;
    private int pageNumber;
    private final String pageSuffix = " / MaxPageNumber";

    int[] helpImageResources = {R.drawable.tutorial1, R.drawable.tutorial2,
        R.drawable.tutorial3, R.drawable.tutorial4, R.drawable.tutorial5,
        R.drawable.helptext1, R.drawable.helptext2};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);

        pageNumber = MinPageNumber;
        updatePageNumber();
    }

    public void next(View view) {
        if (pageNumber == MaxPageNumber)
            pageNumber = MinPageNumber - 1;
        pageNumber++;
        updatePageNumber();
    }

    public void prev(View view) {
        if (pageNumber == MinPageNumber)
            pageNumber = MaxPageNumber + 1;
        pageNumber--;
        updatePageNumber();
    }

    public void close(View view) {
        finish();
    }

    public void updatePageNumber() {
        EditText pageText = (EditText) findViewById(R.id.page);
        pageText.setText(pageNumber + pageSuffix);

        ImageView helpView = (ImageView) findViewById(R.id.helpImage);
        helpView.setImageResource(helpImageResources[pageNumber - 1]);
    }
}
