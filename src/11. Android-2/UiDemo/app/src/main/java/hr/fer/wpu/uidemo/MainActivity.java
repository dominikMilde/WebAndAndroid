package hr.fer.wpu.uidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        Class[] classes = new Class[]{AlertActivity.class, ButtonActivity.class, ImageActivity.class, RadioActivity.class, ScrollActivity.class, SpinnerActivity.class, SwitchActivity.class, TextActivity.class};

        for (Class c:classes) {
            Button button = new Button(this);
            button.setText(c.getSimpleName());
            button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.addView(button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,c);
                    startActivity(intent);
                }
            });
        }

        this.setContentView(layout);
    }
}
