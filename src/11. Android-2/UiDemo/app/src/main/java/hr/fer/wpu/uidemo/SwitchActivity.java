package hr.fer.wpu.uidemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SwitchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        ToggleButton tb = new ToggleButton(this);
        tb.setText("OFF");
        tb.setTextOff("OFF");
        tb.setTextOn("ON");
        tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SwitchActivity.this, "Checked " +
                        ((ToggleButton) v).isChecked(), Toast.LENGTH_SHORT).show();
            }
        });
        layout.addView(tb);
        Switch sw = new Switch(this);
        sw.setText("Enabled");
        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SwitchActivity.this, "Enabled " +
                        ((Switch) v).isChecked(), Toast.LENGTH_SHORT).show();
            }
        });
        layout.addView(sw);
        this.setContentView(layout);

    }
}
