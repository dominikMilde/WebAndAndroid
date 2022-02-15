package hr.fer.wpu.uidemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ScrollActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scrollView = new ScrollView(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);//add 30 rows
        for (int i = 0; i < 50; i++) {
            TextView textView = new TextView(this);
            textView.setText("row " + i);
            layout.addView(textView);
        }
        scrollView.addView(layout);
        this.setContentView(scrollView);

    }
}
