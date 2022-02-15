package hr.fer.wpu.uidemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RadioActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.setOrientation(RadioGroup.VERTICAL);
        RadioButton radioButton1 = new RadioButton(this);
        radioButton1.setText("Excellent");
        radioGroup.addView(radioButton1);
        RadioButton radioButton2 = new RadioButton(this);
        radioButton2.setText("Good");
        radioGroup.addView(radioButton2);
        RadioButton radioButton3 = new RadioButton(this);
        radioButton3.setText("Bad");
        radioGroup.addView(radioButton3);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Toast.makeText(RadioActivity.this, "Checked " + checkedId, Toast.LENGTH_SHORT).show();
            }
        });
        this.setContentView(radioGroup);

    }
}
