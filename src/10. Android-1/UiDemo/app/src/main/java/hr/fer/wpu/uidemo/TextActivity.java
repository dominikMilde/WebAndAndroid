package hr.fer.wpu.uidemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TextActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        TextView textView = new TextView(this);
        textView.setText("TextView");
            layout.addView(textView);

        EditText editText = new EditText(this);

        editText.setHint("EditText");
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Toast.makeText(TextActivity.this, ((EditText)v).getText(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        layout.addView(editText);
        layout.addView(new EditText(this));
        this.setContentView(layout);
    }
}
