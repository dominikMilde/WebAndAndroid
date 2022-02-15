package hr.fer.wpu.specialbroadcaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Date;

public class BroadcasterActivity extends AppCompatActivity {

    private static final String SPECIAL_EVENT = "hr.fer.wpu.SPECIAL_EVENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scrollView = new ScrollView(this);
        final LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        Button button = new Button(this);
        button.setText("Broadcast message");
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SPECIAL_EVENT);
                intent.putExtra("message", new Date().toString());
                sendBroadcast(intent);

                //show broadcasted message
                TextView lastBroadcast = new TextView(BroadcasterActivity.this);
                lastBroadcast.setText("Broadcasting message: " + new Date().toString());
                layout.addView(lastBroadcast);
            }
        });

        layout.addView(button);
        scrollView.addView(layout);
        this.setContentView(scrollView);
    }
}
