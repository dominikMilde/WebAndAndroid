package hr.fer.wpu.uidemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ButtonActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        CheckBox checkBox = new CheckBox(this);
        checkBox.setSelected(true);
        checkBox.setText("I am in");
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(ButtonActivity.this, "Checked " + isChecked, Toast.LENGTH_SHORT).show();
            }
        });
        layout.addView(checkBox);
        Button button = new Button(this);
        button.setText("Button");
        button.setOnClickListener(v -> Toast.makeText(ButtonActivity.this, "Clicked", Toast.LENGTH_SHORT).show());
        layout.addView(button);
        this.setContentView(layout);
    }
}
